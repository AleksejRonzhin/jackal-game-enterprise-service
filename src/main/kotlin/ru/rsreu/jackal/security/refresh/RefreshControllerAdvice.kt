package ru.rsreu.jackal.security.refresh

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.security.refresh.dto.RefreshResponse
import ru.rsreu.jackal.security.refresh.dto.RefreshResponseStatus
import ru.rsreu.jackal.security.refresh.exception.AlreadyUsedRefreshTokenException
import ru.rsreu.jackal.security.refresh.exception.InvalidRefreshTokenException
import ru.rsreu.jackal.security.refresh.exception.InvalidTokensPairException

@RestControllerAdvice
class RefreshControllerAdvice {
    @ExceptionHandler(InvalidTokensPairException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleNonValidTokensPairException(): ResponseEntity<RefreshResponse> =
        ResponseEntity(
            RefreshResponse(responseStatus = RefreshResponseStatus.INVALID_ACCESS_REFRESH_PAIR),
            HttpStatus.UNAUTHORIZED
        )

    @ExceptionHandler(InvalidRefreshTokenException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInvalidRefreshTokenException(): ResponseEntity<RefreshResponse> =
        ResponseEntity(
            RefreshResponse(responseStatus = RefreshResponseStatus.INVALID_REFRESH),
            HttpStatus.UNAUTHORIZED
        )

    @ExceptionHandler(AlreadyUsedRefreshTokenException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAlreadyUsedRefreshTokenException(): ResponseEntity<RefreshResponse> =
        ResponseEntity(
            RefreshResponse(responseStatus = RefreshResponseStatus.ALREADY_USED_REFRESH),
            HttpStatus.UNAUTHORIZED
        )
}