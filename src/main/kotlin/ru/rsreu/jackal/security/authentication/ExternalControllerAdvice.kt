package ru.rsreu.jackal.security.authentication

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.rsreu.jackal.security.authentication.dto.AuthenticationResponse
import ru.rsreu.jackal.security.authentication.dto.AuthenticationResponseStatus
import ru.rsreu.jackal.security.authentication.exception.ExternalAuthenticationException
import ru.rsreu.jackal.security.authentication.service.provider.ExternalAuthenticationProviderType

@ControllerAdvice
class ExternalControllerAdvice {
    @ExceptionHandler(ExternalAuthenticationException::class)
    fun handleExternalAuthenticationException(exception: ExternalAuthenticationException) =
        ResponseEntity(
            AuthenticationResponse(
                responseStatus = when (exception.externalAuthenticationProviderType) {
                    ExternalAuthenticationProviderType.VK ->
                        AuthenticationResponseStatus.VK_PROVIDER_ACCESS_ERROR
                    ExternalAuthenticationProviderType.YANDEX ->
                        AuthenticationResponseStatus.YANDEX_PROVIDER_ACCESS_ERROR
                }
            ), HttpStatus.UNAUTHORIZED
        )
}