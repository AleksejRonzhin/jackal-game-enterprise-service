package ru.rsreu.jackal.api.game.controller.game

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.rsreu.jackal.api.TransformResponseService
import ru.rsreu.jackal.api.game.dto.*
import ru.rsreu.jackal.api.game.service.GameService
import ru.rsreu.jackal.api.user.service.UserService
import ru.rsreu.jackal.shared_models.requests.FinishGameRequest

@RestController
@RequestMapping("/api/game")
class GameController(
    private val gameService: GameService,
    private val userService: UserService,
    private val transformResponseService: TransformResponseService,
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

    @PostMapping("/user-finish")
    fun finish(@RequestParam userId: Long) {
        val user = userService.getUserByIdOrThrow(userId)
        gameService.userFinish(user)
        gameService.sendUserFinishToLobby(userId)
    }

    @PostMapping("/finish")
    fun finish(@RequestBody request: FinishGameRequest) {
        gameService.finish(request.statistics, request.endDate)
        gameService.sendFinishToLobby(request.lobbyId)
    }
}