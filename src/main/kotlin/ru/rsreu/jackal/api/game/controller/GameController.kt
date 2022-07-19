package ru.rsreu.jackal.api.game.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.rsreu.jackal.api.TransformResponseService
import ru.rsreu.jackal.api.game.dto.*
import ru.rsreu.jackal.api.game.service.GameService
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.user.service.UserService
import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.requests.GameNotStartedRequest

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
    fun startGame(authentication: Authentication): ResponseEntity<HttpResponse> {
        val lobbyInfoResponse = lobbyService.getLobbyInfoForStart(authentication.principal.toString().toLong())
        if (lobbyInfoResponse.responseStatus != HttpResponseStatus.OK) {
            return ResponseEntity.ok(HttpResponse(lobbyInfoResponse.responseStatus))
        }

        val gameMode = gameService.getGameModeByIdOrThrow(lobbyInfoResponse.gameModeId!!)
        val createGameSessionResponse =
            gameService.sendCreateGameSessionCreate(gameMode, lobbyInfoResponse.userIds!!, lobbyInfoResponse.lobbyId!!)
        if (createGameSessionResponse.responseStatus != HttpResponseStatus.OK) {
            return ResponseEntity.ok(HttpResponse(createGameSessionResponse.responseStatus))
        }

        lobbyService.sendInfoAboutGameSession(createGameSessionResponse.playerInfos)
        val users = createGameSessionResponse.playerInfos.map { userService.getUserByIdOrThrow(it.userId) }
        gameService.createGameSession(users, createGameSessionResponse.startDate, gameMode)

        return ResponseEntity.ok(HttpResponse(HttpResponseStatus.OK))
    }

    @PostMapping("/resolve-not-started")
    fun resoleNotStarted(@RequestBody request: GameNotStartedRequest): ResponseEntity<Any> {
        println(request)
        return ResponseEntity.ok("")
    }
}