package ru.rsreu.jackal.api.services

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse

@Service
class LobbyService(val userService: UserService, val restTemplate: RestTemplate) {
    fun create(request: CreateLobbyRequest, userName: String): CreateLobbyResponse? {
        val url = "http://localhost:8081/api/lobby/create"

        request.hostId = userService.getUserByName(userName).id
        val response = restTemplate.postForEntity<CreateLobbyResponse>(url, request)
        return response.body
    }

}