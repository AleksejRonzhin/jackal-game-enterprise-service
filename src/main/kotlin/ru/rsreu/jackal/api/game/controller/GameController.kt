package ru.rsreu.jackal.api.game.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.rsreu.jackal.api.TransformResponseService
import ru.rsreu.jackal.api.game.dto.*
import ru.rsreu.jackal.api.game.service.GameService
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.user.service.UserService
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponse
import ru.rsreu.jackal.shared_models.responses.HttpLobbyResponseStatus

@RestController
@RequestMapping("/api/game")
class GameController(
    private val gameService: GameService,
    private val lobbyService: LobbyService,
    private val transformResponseService: TransformResponseService,
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterGameRequest): ResponseEntity<RegisterGameResponse> {
        gameService.registerGame(request.title, request.serviceAddress, request.clientAddress, request.modes)
        return ResponseEntity.ok(RegisterGameResponse(RegisterGameStatus.OK))
    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<GetAllGamesResponse> {
        val games = gameService.getAllGame()
        val gamesInfo = transformResponseService.transformGamesForClient(games)
        return ResponseEntity.ok(GetAllGamesResponse(gamesInfo, GetAllGamesStatus.OK))
    }

    @PostMapping("/start")
    fun startGame(authentication: Authentication): ResponseEntity<HttpLobbyResponse> {
        val getLobbyInfoResponse = lobbyService.getLobbyInfoForStart(authentication.principal.toString().toLong())
        val gameMode = gameService.getGameModeByIdOrThrow(getLobbyInfoResponse.gameModeId)
        val createGameSessionResponse = gameService.sendCreateGameSessionCreate(gameMode.game, getLobbyInfoResponse.userIds)
        lobbyService.sendInfoAboutGameSession(createGameSessionResponse.playerInfos)
        val users = createGameSessionResponse.playerInfos.map { userService.getUserByIdOrThrow(it.userId) }
        gameService.createGameSession(users, createGameSessionResponse.startDate, gameMode)
        return ResponseEntity.ok(HttpLobbyResponse(HttpLobbyResponseStatus.OK))
    }
}