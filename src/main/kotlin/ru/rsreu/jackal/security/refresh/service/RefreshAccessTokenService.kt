package ru.rsreu.jackal.security.refresh.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.rsreu.jackal.security.jwt.JwtTokenProvider
import ru.rsreu.jackal.security.refresh.exception.AlreadyUsedRefreshTokenException
import ru.rsreu.jackal.security.refresh.exception.InvalidRefreshTokenException
import ru.rsreu.jackal.security.refresh.exception.InvalidTokensPairException
import ru.rsreu.jackal.security.user.AuthenticationProviderUserRepository
import ru.rsreu.jackal.security.util.TokenGenerationUtil

@Service
class RefreshAccessTokenService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val generationUtil: TokenGenerationUtil,
    private val authenticationProviderUserRepository: AuthenticationProviderUserRepository,
    private val refreshTokenService: RefreshTokenService
) {
    @Transactional
    fun getNewTokensPair(accessToken: String, refreshToken: String): Pair<String, String> {
        val refreshClaims = jwtTokenProvider.runCatching {
            getJwsClaims(refreshToken)
        }.onFailure { throw InvalidRefreshTokenException() }.getOrThrow()
        val accessFromRefresh = jwtTokenProvider.getJwtFromRefreshClaims(refreshClaims)
        if (accessFromRefresh != accessToken) {
            throw InvalidTokensPairException()
        }
        if (refreshTokenService.deleteRefreshToken(refreshToken) == 0L) {
            throw AlreadyUsedRefreshTokenException()
        }
        val authenticationProviderUser = authenticationProviderUserRepository.findByUserName(
            jwtTokenProvider.getUsernameFromRefreshClaims(refreshClaims)
        )
        return generationUtil.generateAccessRefreshPair(authenticationProviderUser)
    }
}