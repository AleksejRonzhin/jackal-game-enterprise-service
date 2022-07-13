package ru.rsreu.jackal.security.authentication.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.services.UserService
import ru.rsreu.jackal.security.authentication.service.provider.ExternalAuthenticationProvider
import ru.rsreu.jackal.security.authentication.service.provider.ExternalAuthenticationProviderType
import ru.rsreu.jackal.security.user.AuthenticationProviderUser
import ru.rsreu.jackal.security.user.AuthenticationProviderUserRepository
import ru.rsreu.jackal.security.refresh.RefreshToken
import ru.rsreu.jackal.security.refresh.RefreshTokenRepository

@Service
class ExternalAuthenticationService(
    val authenticationProviderUserRepository: AuthenticationProviderUserRepository,
    val userService: UserService,
    val refreshTokenRepository: RefreshTokenRepository,

    @Qualifier("vkAuthenticationProvider")
    val vkAuthenticationProvider: ExternalAuthenticationProvider,

    @Qualifier("yandexAuthenticationProvider")
    val yandexAuthenticationProvider: ExternalAuthenticationProvider
) {
    fun authenticateViaYandex(accessToken: String): AuthenticationProviderUser {
        return performAuthentication(accessToken, yandexAuthenticationProvider)
    }

    fun authenticateViaVk(accessToken: String): AuthenticationProviderUser {
        return performAuthentication(accessToken, vkAuthenticationProvider)
    }

    private fun performAuthentication(
        accessToken: String,
        externalAuthenticationProvider: ExternalAuthenticationProvider
    ): AuthenticationProviderUser {
        val (principal, username) = externalAuthenticationProvider.getAuthentication(accessToken)
        return getAuthenticationProviderUser(
            principal = principal,
            username = username,
            provider = externalAuthenticationProvider.providerType
        )
    }

    private fun getAuthenticationProviderUser(
        principal: String, username: String, provider: ExternalAuthenticationProviderType
    ) = (authenticationProviderUserRepository.findByAuthenticationPrincipal(principal)
        ?: createAuthenticationProviderUser(principal, username, provider))

    private fun createAuthenticationProviderUser(
        principal: String, username: String, authenticationProvider: ExternalAuthenticationProviderType
    ): AuthenticationProviderUser {
        val user = userService.create(username)
        return authenticationProviderUserRepository.save(
            AuthenticationProviderUser(
                user = user,
                authenticationPrincipal = principal,
                externalAuthenticationProvider = authenticationProvider,
                refreshTokens = emptyList()
            )
        )
    }

    fun createRefreshToken(refreshToken: String, user: AuthenticationProviderUser) {
        refreshTokenRepository.save(RefreshToken(value = refreshToken, authenticationProviderUser = user))
    }
}