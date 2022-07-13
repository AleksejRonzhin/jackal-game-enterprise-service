package ru.rsreu.jackal.security.refresh.service

import org.springframework.stereotype.Component
import ru.rsreu.jackal.security.refresh.model.RefreshToken
import ru.rsreu.jackal.security.refresh.repository.RefreshTokenRepository
import ru.rsreu.jackal.security.user.AuthenticationProviderUser

@Component
class RefreshTokenService(private val refreshTokenRepository: RefreshTokenRepository) {
    fun createRefreshToken(refreshToken: String, user: AuthenticationProviderUser) {
        refreshTokenRepository.save(RefreshToken(value = refreshToken, authenticationProviderUser = user))
    }

    fun deleteRefreshToken(refreshToken: String) =
        refreshTokenRepository.deleteByValue(refreshToken)
}