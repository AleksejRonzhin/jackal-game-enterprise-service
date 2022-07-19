package ru.rsreu.jackal.api.lobby.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.ResourceAccessException
import ru.rsreu.jackal.api.game.exception.GameModeNotFoundException
import ru.rsreu.jackal.api.lobby.exception.LobbyServiceFailException
import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus

@ControllerAdvice
class LobbyControllerAdvice {
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
}