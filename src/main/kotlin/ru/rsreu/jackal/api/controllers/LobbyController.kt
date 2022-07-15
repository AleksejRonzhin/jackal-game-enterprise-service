package ru.rsreu.jackal.api.controllers

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.rsreu.jackal.api.controllers.dto.CreateLobbyClientRequest
import ru.rsreu.jackal.api.controllers.dto.PreConnectLobbyClientRequest
import ru.rsreu.jackal.api.services.LobbyService
import ru.rsreu.jackal.shared_models.responses.PreConnectLobbyResponse
import ru.rsreu.jackal.shared_models.responses.ReconnectLobbyResponse


@RestController
@RequestMapping("/api/lobby")
class LobbyController(val service: LobbyService) {
    @PostMapping("/create")
    fun create(
        @RequestBody request: CreateLobbyClientRequest, authentication: Authentication
    ): ResponseEntity<PreConnectLobbyResponse> {
        return ResponseEntity.ok(
            service.create(
                request.lobbyTitle, request.lobbyPassword, authentication.principal.toString().toLong()
            )
        )
    }

    @PostMapping("/pre-connect")
    fun preConnect(
        @RequestBody request: PreConnectLobbyClientRequest, authentication: Authentication
    ): ResponseEntity<PreConnectLobbyResponse> {
        return ResponseEntity.ok(
            service.preConnect(
                request.lobbyTitle, request.lobbyPassword, authentication.principal.toString().toLong()
            )
        )
    }

    @GetMapping("/reconnect")
    fun reconnect(authentication: Authentication): ResponseEntity<ReconnectLobbyResponse> {
        return ResponseEntity.ok(
            service.reconnect(authentication.principal.toString().toLong())
        )
    }
}