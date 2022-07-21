package ru.rsreu.jackal.api.game.controller.connection

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.game.service.GameConnectionService
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.responses.GetConnectionInfoResponse

@RestController
@RequestMapping("/api/game")
class GameConnectionController(private val service: GameConnectionService) {

    @GetMapping("/get-connection-info")
    fun getConnectionInfo(authentication: Authentication): ResponseEntity<GetConnectionInfoResponse> {
        val playerInfo = service.getConnectionInfoForUser(authentication.principal.toString().toLong())
        return ResponseEntity.ok(
            GetConnectionInfoResponse(playerInfo.webSocketInfo, playerInfo.jwt, HttpResponseStatus.OK)
        )
    }
}