package ru.rsreu.jackal.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.models.requests.CreateLobbyRequest
import ru.rsreu.jackal.models.responses.CreateLobbyResponse
import ru.rsreu.jackal.services.LobbyService

@RestController
@RequestMapping("/api/lobby")
class LobbyController(val service: LobbyService) {

    @PostMapping("/create")
    fun create(@RequestBody request: CreateLobbyRequest): CreateLobbyResponse{
        request.hostId = 100
        return service.create(request)
    }
}