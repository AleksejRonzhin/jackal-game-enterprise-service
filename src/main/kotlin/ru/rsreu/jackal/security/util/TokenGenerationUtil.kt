package ru.rsreu.jackal.security.util

import org.springframework.stereotype.Component
import ru.rsreu.jackal.security.jwt.JwtTokenProvider
import ru.rsreu.jackal.security.refresh.service.RefreshTokenService
import ru.rsreu.jackal.security.user.AuthenticationProviderUser

@Component
class TokenGenerationUtil(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenService: RefreshTokenService) {

    fun generateAccessRefreshPair(authenticationProviderUser: AuthenticationProviderUser): Pair<String, String> {
        val accessToken = jwtTokenProvider.createAccessToken(authenticationProviderUser.user)
        val refreshToken = jwtTokenProvider.createRefreshToken(accessToken, authenticationProviderUser.user.name)
        refreshTokenService.createRefreshToken(refreshToken, authenticationProviderUser)
        return Pair(accessToken, refreshToken)
    }
}