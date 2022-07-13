package ru.rsreu.jackal.security.authentication.service.provider

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import ru.rsreu.jackal.security.authentication.exception.ExternalAuthenticationException

@Component("yandexAuthenticationProvider")
class YandexAuthenticationProvider(httpClient: RestTemplate) : ExternalAuthenticationProvider(httpClient) {
    @Value("\${security.oauth.yandex.auth_url}")
    private lateinit var yandexAuthUrl: String

    override val providerType: ExternalAuthenticationProviderType = ExternalAuthenticationProviderType.YANDEX

    override fun getAuthentication(accessToken: String): ExternalAccessTokenAuthentication {
        val yandexAuthResponseNode = performOAuthYandexCodeFlow(
            url = yandexAuthUrl,
            accessToken = accessToken
        )
        val yandexPrincipal = yandexAuthResponseNode.path("default_email").asText()
        return ExternalAccessTokenAuthentication(yandexPrincipal, yandexPrincipal)
    }

    private fun prepareYandexOAuthRequestEntity(accessToken: String): HttpEntity<Any> {
        val headers = HttpHeaders()
        headers["Authorization"] = "OAuth $accessToken"
        return HttpEntity(headers)
    }

    private fun performOAuthYandexCodeFlow(
        url: String, accessToken: String
    ) = httpClient
        .runCatching {
            exchange<String>(url, HttpMethod.GET, prepareYandexOAuthRequestEntity(accessToken)).getJsonNode()
        }.onFailure { throw ExternalAuthenticationException(providerType) }.getOrThrow()
}