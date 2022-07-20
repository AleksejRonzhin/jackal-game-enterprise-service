package ru.rsreu.jackal.api.game.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.ResourceAccessException
import ru.rsreu.jackal.api.game.exception.GameModeNotFoundException
import ru.rsreu.jackal.api.game.exception.UsersInLobbyTooMuchException
import ru.rsreu.jackal.api.game.exception.UsersInLobbyTooSmallException
import ru.rsreu.jackal.api.lobby.exception.LobbyServiceFailException
import ru.rsreu.jackal.api.user.dto.GetUserInfoResponse
import ru.rsreu.jackal.api.user.dto.GetUserInfoStatus
import ru.rsreu.jackal.api.user.exception.UserNotFoundException
import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus

@RestControllerAdvice(basePackageClasses = [GameController::class])
class GameControllerAdvice{
    @ExceptionHandler(GameModeNotFoundException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleGameModeIsNotFoundException(): ResponseEntity<HttpResponse> = ResponseEntity.ok(
        HttpResponse(HttpResponseStatus.GAME_NOT_FOUND)
    )

    @ExceptionHandler(LobbyServiceFailException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyServiceNotAvailable(): ResponseEntity<HttpResponse> = ResponseEntity.ok(
        HttpResponse(HttpResponseStatus.LOBBY_SERVICE_FAIL)
    )

    @ExceptionHandler(ResourceAccessException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleResourceAccessException(): ResponseEntity<HttpResponse> = ResponseEntity.ok(
        HttpResponse(HttpResponseStatus.LOBBY_SERVICE_NOT_AVAILABLE)
    )

    @ExceptionHandler(UsersInLobbyTooMuchException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUsersInLobbyTooMuchException(): ResponseEntity<HttpResponse> = ResponseEntity.ok(
        HttpResponse(HttpResponseStatus.USERS_IN_LOBBY_TOO_MUCH)
    )

    @ExceptionHandler(UsersInLobbyTooSmallException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUsersInLobbyTooSmallException(): ResponseEntity<HttpResponse> = ResponseEntity.ok(
        HttpResponse(HttpResponseStatus.USERS_IN_LOBBY_TOO_SMALL)
    )

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleHostAlreadyInLobbyException(): ResponseEntity<GetUserInfoResponse> = ResponseEntity.ok(
        GetUserInfoResponse(responseStatus = GetUserInfoStatus.USER_NOT_FOUND)
    )
}