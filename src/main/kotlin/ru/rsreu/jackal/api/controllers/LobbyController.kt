package ru.rsreu.jackal.api.controllers

import org.springframework.web.bind.annotation.*
import ru.rsreu.jackal.api.models.requests.CreateLobbyRequest
import ru.rsreu.jackal.api.models.responses.CreateLobbyResponse
import ru.rsreu.jackal.api.services.LobbyService

@RestController
@RequestMapping("/api/lobby")
class LobbyController(val service: LobbyService) {

    @PostMapping("/create")
    fun create(@RequestBody request: CreateLobbyRequest): CreateLobbyResponse {
        request.hostId = 100
        return service.create(request)
    }
}