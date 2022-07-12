package ru.rsreu.jackal.security.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForEntity
import ru.rsreu.jackal.api.services.UserService
import ru.rsreu.jackal.security.user.AuthenticationProviderUser
import ru.rsreu.jackal.security.user.AuthenticationProviderUserRepository
import ru.rsreu.jackal.security.user.ExternalAuthenticationProvider

@Service
class ExternalAuthenticationService(
    val httpClient: RestTemplate,
    val authenticationProviderUserRepository: AuthenticationProviderUserRepository,
    val userService: UserService
) {
    fun authenticateViaYandex(accessToken: String): AuthenticationProviderUser {
        fun prepareYandexOAuthRequestEntity(accessToken: String): HttpEntity<Any> {
            val headers = HttpHeaders()
            headers["Authorization"] = "OAuth $accessToken"
            return HttpEntity(headers)
        }

        val yandexPrincipal = getPrincipalFromResponse(
            response = performOAuthCodeFlow(
                url = "https://login.yandex.ru/info?format=json",
                accessToken = accessToken,
                requestEntityPreparer = ::prepareYandexOAuthRequestEntity
            ),
            principalKey = "default_email"
        )
        return authenticationProviderUserRepository.findByAuthenticationPrincipal(yandexPrincipal)
            ?: createAuthenticationProviderUser(yandexPrincipal, ExternalAuthenticationProvider.YANDEX)
    }

    fun authenticateViaVk(accessToken: String): String {
        println(accessToken)
        val response = httpClient.getForEntity<String>("https://api.vk.com/method/account.getProfileInfo?access_token=$accessToken&v=5.131")
        return response.body!!
    }

    fun getVkAccessToken(authorizationCode: String): String {
        return getPrincipalFromResponse(httpClient.getForEntity<String>(
            "https://oauth.vk.com/access_token?client_id=8216954" +
                    "&client_secret=hJXK3GjJ0v2owpG1hwRX&redirect_uri=https://oauth.vk.com/blank.html&code=$authorizationCode"), "access_token")
    }

    private fun performOAuthCodeFlow(
        url: String,
        accessToken: String,
        requestEntityPreparer: (String) -> HttpEntity<Any>
    ): ResponseEntity<String> {
        fun performGettingDataFromResourceServer(
            oauthUrl: String,
            accessToken: String,
            requestEntityPreparer: (String) -> HttpEntity<Any>
        ) = httpClient.exchange<String>(oauthUrl, HttpMethod.GET, requestEntityPreparer(accessToken))
        val response = performGettingDataFromResourceServer(
            url,
            accessToken,
            requestEntityPreparer
        )
        if (response.statusCode != HttpStatus.OK) {
            // TODO Exception types
            throw IllegalArgumentException()
        }
        return response
    }

    private fun getPrincipalFromResponse(
        response: ResponseEntity<String>,
        principalKey: String
    ) = ObjectMapper().readTree(response.body).path(principalKey).asText()

    private fun createAuthenticationProviderUser(
        principal: String,
        authenticationProvider: ExternalAuthenticationProvider
    ): AuthenticationProviderUser {
        val user = userService.create(principal)
        return authenticationProviderUserRepository.save(
            AuthenticationProviderUser(
                user = user,
                authenticationPrincipal = principal,
                externalAuthenticationProvider = authenticationProvider
            )
        )
    }
}