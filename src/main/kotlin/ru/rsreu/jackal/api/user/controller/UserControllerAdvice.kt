package ru.rsreu.jackal.api.user.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rsreu.jackal.api.user.dto.GetUserInfoResponse
import ru.rsreu.jackal.api.user.dto.GetUserInfoStatus
import ru.rsreu.jackal.api.user.exception.UserNotFoundException

@RestControllerAdvice
class UserControllerAdvice {
    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleHostAlreadyInLobbyException(): ResponseEntity<GetUserInfoResponse> = ResponseEntity.ok(
        GetUserInfoResponse(responseStatus = GetUserInfoStatus.USER_NOT_FOUND)
    )
}