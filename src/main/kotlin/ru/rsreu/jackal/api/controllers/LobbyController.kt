package ru.rsreu.jackal.api.controllers

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.services.LobbyService
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse

@RestController
@RequestMapping("/api/lobby")
class LobbyController(val service: LobbyService) {
    @PostMapping("/create")
    fun create(@RequestBody request: CreateLobbyRequest, authentication: Authentication): ResponseEntity<CreateLobbyResponse> {
        return ResponseEntity.ok(service.create(request, authentication.principal.toString().toLong()))
    }
}