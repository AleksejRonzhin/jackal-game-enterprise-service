package ru.rsreu.jackal.api.game.controller.connection

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import ru.rsreu.jackal.api.game.exception.GameServiceFailException
import ru.rsreu.jackal.api.game.exception.GameServiceNotAvailableException
import ru.rsreu.jackal.api.game.exception.UserNotInAnyGameSessionException
import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus

@ControllerAdvice(basePackageClasses = [GameConnectionController::class])
class GameConnectionControllerAdvice {
    @ExceptionHandler(UserNotInAnyGameSessionException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleUserNotInAnyGameSessionException(): ResponseEntity<HttpResponse> {
        return ResponseEntity.ok(HttpResponse(HttpResponseStatus.USER_NOT_IN_GAME))
    }

    @ExceptionHandler(GameServiceFailException::class)
    fun handleGameServiceFailException(): ResponseEntity<HttpResponse> {
        return ResponseEntity.ok(HttpResponse((HttpResponseStatus.GAME_SERVICE_FAIL)))
    }

    @ExceptionHandler(GameServiceNotAvailableException::class)
    fun handleGameServiceNotAvailableException(): ResponseEntity<HttpResponse> {
        return ResponseEntity.ok(HttpResponse((HttpResponseStatus.GAME_SERVICE_NOT_AVAILABLE)))
    }
}