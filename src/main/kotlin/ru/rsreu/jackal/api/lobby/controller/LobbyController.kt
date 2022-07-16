package ru.rsreu.jackal.api.lobby.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.rsreu.jackal.api.TransformResponseService
import ru.rsreu.jackal.api.game.service.GameService
import ru.rsreu.jackal.api.lobby.dto.*
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.shared_models.responses.ChangeGameResponse
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse
import ru.rsreu.jackal.shared_models.responses.GetLobbyConnectionInfoResponse
import ru.rsreu.jackal.shared_models.responses.JoinLobbyResponse


@RestController
@RequestMapping("/api/lobby")
class LobbyController(
    val lobbyService: LobbyService, val transformResponseService: TransformResponseService, val gameService: GameService
) {
    @PostMapping("/create")
    fun create(
        @RequestBody request: CreateLobbyClientRequest, authentication: Authentication
    ): ResponseEntity<CreateLobbyResponse> {
        return ResponseEntity.ok(
            lobbyService.create(
                request.lobbyTitle, request.lobbyPassword, authentication.principal.toString().toLong()
            )
        )
    }

    @PostMapping("/join")
    fun join(
        @RequestBody request: JoinLobbyClientRequest, authentication: Authentication
    ): ResponseEntity<JoinLobbyResponse> {
        return ResponseEntity.ok(
            lobbyService.join(
                request.lobbyTitle, request.lobbyPassword, authentication.principal.toString().toLong()
            )
        )
    }

    @GetMapping("/connection-info")
    fun getInfoAboutConnection(authentication: Authentication): ResponseEntity<GetLobbyConnectionInfoResponse> {
        return ResponseEntity.ok(
            lobbyService.getInfoAboutSocketConnection(authentication.principal.toString().toLong())
        )
    }

    @PostMapping("/change-game")
    fun changeGame(
        @RequestBody request: ChangeGameClientRequest, authentication: Authentication
    ): ResponseEntity<ChangeGameResponse> {
        val gameId = request.gameId
        gameService.checkGameIsExistsOrThrow(gameId)
        return ResponseEntity.ok(lobbyService.changeGame(gameId, authentication.principal.toString().toLong()))
    }

    @GetMapping("/all")
    fun getAllLobbyInfo(): ResponseEntity<GetAllLobbiesInfoClientResponse> {
        val lobbies = lobbyService.getClientLobbiesInfo()
        return ResponseEntity.ok(
            GetAllLobbiesInfoClientResponse(
                transformResponseService.transformLobbiesForClient(
                    lobbies
                ), GetAllLobbyInfoClientStatus.OK
            )
        )
    }
}