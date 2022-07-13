package ru.rsreu.jackal.security.authentication

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.security.authentication.dto.AuthenticationRequest
import ru.rsreu.jackal.security.authentication.dto.AuthenticationResponse
import ru.rsreu.jackal.security.authentication.dto.AuthenticationResponseStatus
import ru.rsreu.jackal.security.authentication.service.ExternalAuthenticationService
import ru.rsreu.jackal.security.jwt.JwtTokenProvider
import ru.rsreu.jackal.security.refresh.service.RefreshTokenService
import ru.rsreu.jackal.security.user.AuthenticationProviderUser
import ru.rsreu.jackal.security.util.TokenGenerationUtil

@RestController
@RequestMapping("/api/auth")
class ExternalAuthenticationController(
    private val externalAuthenticationService: ExternalAuthenticationService,
    private val tokenGenerationUtil: TokenGenerationUtil
) {

    @PostMapping("/yandex")
    fun authViaYandex(@RequestBody body: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(
            formSuccessAuthenticationResponse(externalAuthenticationService.authenticateViaYandex(body.accessToken))
        )
    }

    @PostMapping("/vk")
    fun authViaVk(@RequestBody body: AuthenticationRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(
            formSuccessAuthenticationResponse(externalAuthenticationService.authenticateViaVk(body.accessToken))
        )
    }

    private fun formSuccessAuthenticationResponse(
        authenticationProviderUser: AuthenticationProviderUser
    ): AuthenticationResponse {
        val (accessToken, refreshToken) = tokenGenerationUtil.generateAccessRefreshPair(authenticationProviderUser)
        return AuthenticationResponse(
            accessToken,
            refreshToken,
            AuthenticationResponseStatus.OK
        )
    }
}