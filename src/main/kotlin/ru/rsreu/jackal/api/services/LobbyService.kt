package ru.rsreu.jackal.api.services

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import ru.rsreu.jackal.configuration.LobbyServiceConfiguration
import ru.rsreu.jackal.shared_models.requests.CreateLobbyServiceRequest
import ru.rsreu.jackal.shared_models.responses.CreateLobbyResponse

@Service
class LobbyService(
    val restTemplate: RestTemplate,
    val lobbyServiceConfiguration: LobbyServiceConfiguration
) {
    fun create(lobbyName: String, lobbyPassword: String?, hostId: Long) =
        restTemplate.postForEntity<CreateLobbyResponse>(
            lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.lobbyCreationUrlPart,
            CreateLobbyServiceRequest(
                lobbyName, lobbyPassword, hostId
            )
        ).body
}