package ru.rsreu.jackal.api.lobby.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity
import ru.rsreu.jackal.api.lobby.dto.ClientLobbyInfo
import ru.rsreu.jackal.api.lobby.dto.ClientLobbyMemberInfo
import ru.rsreu.jackal.configuration.LobbyServiceConfiguration
import ru.rsreu.jackal.shared_models.LobbyInfo
import ru.rsreu.jackal.shared_models.LobbyMemberInfo
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.requests.JoinLobbyRequest
import ru.rsreu.jackal.shared_models.responses.*

@Service
class LobbyService(
    val restTemplate: RestTemplate, val lobbyServiceConfiguration: LobbyServiceConfiguration
) {
    fun create(lobbyName: String, lobbyPassword: String?, hostId: Long) =
        restTemplate.postForEntity<CreateLobbyResponse>(
            lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.lobbyCreationUrlPart,
            CreateLobbyRequest(lobbyName, lobbyPassword, hostId)
        ).body

    fun join(lobbyTitle: String, lobbyPassword: String?, userId: Long) = restTemplate.postForEntity<JoinLobbyResponse>(
        lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.lobbyJoinUrlPart,
        JoinLobbyRequest(lobbyTitle, lobbyPassword, userId)
    ).body

    fun getInfoAboutSocketConnection(userId: Long) = restTemplate.getForEntity<GetLobbyConnectionInfoResponse>(
        lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.lobbyConnectionInfoUrlPart + "/userId=${userId}"
    ).body

    fun changeGame(userId: Long, gameId: Long): ChangeGameResponse? {
        return null
    }

    fun getClientLobbiesInfo(): Collection<LobbyInfo> =
        restTemplate.getForEntity<GetAllLobbiesResponse>(
            lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.getAllLobbiesUrlPart
        ).body!!.lobbies
}