package ru.rsreu.jackal.api.game.controller.start

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.RestTemplate
import ru.rsreu.jackal.api.game.exception.GameSessionCreationException
import ru.rsreu.jackal.api.lobby.exception.LobbyNotAvailableException
import ru.rsreu.jackal.api.lobby.exception.LobbyServiceFailException
import ru.rsreu.jackal.api.lobby.service.LobbyHttpSender
import ru.rsreu.jackal.configuration.LobbyServiceConfiguration
import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.requests.GameSessionCreationError
import ru.rsreu.jackal.shared_models.requests.GameSessionCreationErrorRequest
import java.util.*

@ControllerAdvice(basePackageClasses = [StartGameController::class])
class StartGameControllerAdvice(
    restTemplate: RestTemplate, private val lobbyServiceConfiguration: LobbyServiceConfiguration
) {
    val sender = LobbyHttpSender(restTemplate, lobbyServiceConfiguration)

    @ExceptionHandler(LobbyServiceFailException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleLobbyServiceNotAvailable(): ResponseEntity<HttpResponse> = ResponseEntity.ok(
        HttpResponse(HttpResponseStatus.LOBBY_SERVICE_FAIL)
    )

    @ExceptionHandler(LobbyNotAvailableException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleResourceAccessException(): ResponseEntity<HttpResponse> = ResponseEntity.ok(
        HttpResponse(HttpResponseStatus.LOBBY_SERVICE_NOT_AVAILABLE)
    )

    @ExceptionHandler(GameSessionCreationException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleGameSessionCreationException(exception: GameSessionCreationException): ResponseEntity<HttpResponse> {
        sendErrorToLobby(exception.lobbyId, exception.error)
        return ResponseEntity.ok(HttpResponse(HttpResponseStatus.GAME_SESSION_CREATION_ERROR))
    }

    private fun sendErrorToLobby(lobbyId: UUID, error: GameSessionCreationError) {
        sender.sendPostToApiUrl<Any>(
            lobbyServiceConfiguration.sendGameSessionCreationErrorInfoUrlPart,
            GameSessionCreationErrorRequest(lobbyId, error)
        )
    }
}