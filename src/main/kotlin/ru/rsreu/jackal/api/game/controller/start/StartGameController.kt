package ru.rsreu.jackal.api.game.controller.start

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.game.service.StartGameService
import ru.rsreu.jackal.shared_models.HttpResponse

@RestController
@RequestMapping("/api/game")
class StartGameController(private val startGameService: StartGameService) {
    @PostMapping("/start")
    fun startGame(authentication: Authentication): ResponseEntity<HttpResponse> {
        val responseStatus = startGameService.start(authentication.principal.toString().toLong())
        return ResponseEntity.ok(HttpResponse(responseStatus))
    }
}