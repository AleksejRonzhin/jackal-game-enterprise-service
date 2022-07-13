package ru.rsreu.jackal.security.authentication.service.provider

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import ru.rsreu.jackal.security.authentication.exception.ExternalAuthenticationException

@Component(value = "vkAuthenticationProvider")
class VkAuthenticationProvider(httpClient: RestTemplate) : ExternalAuthenticationProvider(httpClient) {
    @Value("\${security.oauth.vk.auth_url}")
    private lateinit var vkAuthUrl: String

    @Value("\${security.oauth.vk.api_version}")
    private lateinit var vkApiVersion: String

    override val providerType: ExternalAuthenticationProviderType = ExternalAuthenticationProviderType.VK

    override fun getAuthentication(accessToken: String): ExternalAccessTokenAuthentication {
        val responseNode = performOAuthVkCodeFlow(formFullVkAuthUrl(accessToken))
        return ExternalAccessTokenAuthentication(
            principal = responseNode.getStringByPath("id"),
            username = responseNode.getStringByPath("screen_name")
        )
    }

    private fun performOAuthVkCodeFlow(url: String) = httpClient
        .runCatching {
            getForEntity<String>(url).getJsonNode().path("response").first()
        }.onFailure { throw ExternalAuthenticationException(providerType) }.getOrThrow()

    private fun formFullVkAuthUrl(accessToken: String) = "$vkAuthUrl&access_token=$accessToken&v=$vkApiVersion"
}