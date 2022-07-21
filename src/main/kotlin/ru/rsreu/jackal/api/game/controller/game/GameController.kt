package ru.rsreu.jackal.api.game.controller.game

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.rsreu.jackal.api.TransformResponseService
import ru.rsreu.jackal.api.game.dto.*
import ru.rsreu.jackal.api.game.service.GameService
import ru.rsreu.jackal.shared_models.requests.GameNotStartedRequest

@RestController
@RequestMapping("/api/game")
class GameController(
    private val gameService: GameService,
    private val transformResponseService: TransformResponseService
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

    @PostMapping("/resolve-not-started")
    fun resoleNotStarted(@RequestBody request: GameNotStartedRequest): ResponseEntity<Any> {
        println(request)
        return ResponseEntity.ok("")
    }
}