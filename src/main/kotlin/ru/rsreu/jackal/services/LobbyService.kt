package ru.rsreu.jackal.services

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import ru.rsreu.jackal.models.requests.CreateLobbyRequest
import ru.rsreu.jackal.models.responses.CreateLobbyResponse

@Service
class LobbyService {
    fun create(request: CreateLobbyRequest) : CreateLobbyResponse {
        val restTemplate = RestTemplate()
        val url = "http://localhost:8081/api/lobby/create"
        val response = restTemplate.postForEntity<CreateLobbyResponse>(url, request)
        return response.body!!
    }
}