package ru.rsreu.jackal.api.services

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import ru.rsreu.jackal.api.controllers.dto.PreConnectLobbyServiceRequest
import ru.rsreu.jackal.configuration.LobbyServiceConfiguration
import ru.rsreu.jackal.shared_models.requests.CreateLobbyServiceRequest
import ru.rsreu.jackal.shared_models.responses.PreConnectLobbyResponse

@Service
class LobbyService(
    val restTemplate: RestTemplate,
    val lobbyServiceConfiguration: LobbyServiceConfiguration
) {
    fun preConnect(lobbyTitle: String, lobbyPassword: String?, userId: Long) =
        restTemplate.postForEntity<PreConnectLobbyResponse>(
            lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.lobbyPreConnectUrlPart,
            PreConnectLobbyServiceRequest(lobbyTitle, lobbyPassword, userId)
            ).body
            
    fun create(lobbyName: String, lobbyPassword: String?, hostId: Long) =
        restTemplate.postForEntity<PreConnectLobbyResponse>(
            lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.lobbyCreationUrlPart,
            CreateLobbyServiceRequest(lobbyName, lobbyPassword, hostId)
        ).body
}