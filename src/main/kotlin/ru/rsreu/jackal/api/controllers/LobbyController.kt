package ru.rsreu.jackal.api.controllers

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.rsreu.jackal.api.controllers.dto.CreateLobbyClientRequest
import ru.rsreu.jackal.api.controllers.dto.JoinLobbyClientRequest
import ru.rsreu.jackal.api.services.LobbyService
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse
import ru.rsreu.jackal.shared_models.responses.GetLobbyConnectionInfoResponse
import ru.rsreu.jackal.shared_models.responses.JoinLobbyResponse


@RestController
@RequestMapping("/api/lobby")
class LobbyController(val service: LobbyService) {
    @PostMapping("/create")
    fun create(
        @RequestBody request: CreateLobbyClientRequest, authentication: Authentication
    ): ResponseEntity<CreateLobbyResponse> {
        return ResponseEntity.ok(
            service.create(
                request.lobbyTitle, request.lobbyPassword, authentication.principal.toString().toLong()
            )
        )
    }

    @PostMapping("/join")
    fun join(
        @RequestBody request: JoinLobbyClientRequest, authentication: Authentication
    ): ResponseEntity<JoinLobbyResponse> {
        return ResponseEntity.ok(
            service.join(
                request.lobbyTitle, request.lobbyPassword, authentication.principal.toString().toLong()
            )
        )
    }

    @GetMapping("/connection-info")
    fun getInfoAboutConnection(authentication: Authentication): ResponseEntity<GetLobbyConnectionInfoResponse> {
        return ResponseEntity.ok(
            service.getInfoAboutSocketConnection(authentication.principal.toString().toLong())
        )
    }
}