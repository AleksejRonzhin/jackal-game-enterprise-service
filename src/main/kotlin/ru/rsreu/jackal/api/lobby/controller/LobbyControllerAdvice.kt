package ru.rsreu.jackal.api.lobby.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import ru.rsreu.jackal.api.game.exception.GameNotFoundException
import ru.rsreu.jackal.shared_models.responses.ChangeGameResponse
import ru.rsreu.jackal.shared_models.responses.ChangeGameStatus

@ControllerAdvice
class LobbyControllerAdvice {
    @ExceptionHandler(GameNotFoundException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleGameIsNotFoundException(): ResponseEntity<ChangeGameResponse> = ResponseEntity.ok(
        ChangeGameResponse(responseStatus = ChangeGameStatus.GAME_IS_NOT_FOUND)
    )
}