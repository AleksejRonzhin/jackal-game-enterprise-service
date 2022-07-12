package ru.rsreu.jackal.security.authentication

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.security.authentication.dto.VkAuthenticationRequest
import ru.rsreu.jackal.security.authentication.dto.YandexAuthenticationRequest
import ru.rsreu.jackal.security.user.AuthenticationProviderUser

@RestController
@RequestMapping("/api/auth")
class ExternalAuthenticationController(val externalAuthenticationService: ExternalAuthenticationService) {

    @PostMapping("/yandex")
    fun authViaYandex(@RequestBody body: YandexAuthenticationRequest): ResponseEntity<AuthenticationProviderUser> {
        return ResponseEntity.ok(externalAuthenticationService.authenticateViaYandex(body.accessToken))
    }

    @PostMapping("/vk")
    fun authViaVk(@RequestBody body: VkAuthenticationRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(
            externalAuthenticationService.authenticateViaVk(
                externalAuthenticationService.getVkAccessToken(
                    body.authorizationCode
                )
            )
        )
    }
}