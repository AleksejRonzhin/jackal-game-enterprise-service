package ru.rsreu.jackal.api.lobby.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity
import ru.rsreu.jackal.api.lobby.exception.LobbyServiceFailException
import ru.rsreu.jackal.configuration.LobbyServiceConfiguration
import ru.rsreu.jackal.shared_models.requests.ChangeGameRequest
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.requests.JoinLobbyRequest
import ru.rsreu.jackal.shared_models.requests.SendGameSessionConnectionInfoRequest
import ru.rsreu.jackal.shared_models.responses.*

@Service
class LobbyService(
    private val restTemplate: RestTemplate, private val lobbyServiceConfiguration: LobbyServiceConfiguration
) {
    fun create(lobbyName: String, lobbyPassword: String?, hostId: Long): CreateLobbyResponse =
        restTemplate.postForEntity<CreateLobbyResponse>(
            lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.createLobbyUrlPart,
            CreateLobbyRequest(lobbyName, lobbyPassword, hostId)
        ).body ?: throw LobbyServiceFailException()

    fun join(lobbyTitle: String, lobbyPassword: String?, userId: Long) = restTemplate.postForEntity<JoinLobbyResponse>(
        lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.joinLobbyUrlPart,
        JoinLobbyRequest(lobbyTitle, lobbyPassword, userId)
    ).body ?: throw LobbyServiceFailException()

    fun getInfoAboutSocketConnection(userId: Long): GetLobbyConnectionInfoResponse =
        restTemplate.getForEntity<GetLobbyConnectionInfoResponse>(
            lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.getLobbyConnectionInfoUrlPart + "?userId=${userId}"
        ).body ?: throw LobbyServiceFailException()

    fun changeGame(gameModeId: Long, userId: Long): ChangeGameResponse = restTemplate.postForEntity<ChangeGameResponse>(
        lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.changeGameUrlPart,
        ChangeGameRequest(gameModeId, userId)
    ).body ?: throw LobbyServiceFailException()

    fun getAllLobbiesInfo(): GetAllLobbiesResponse = restTemplate.getForEntity<GetAllLobbiesResponse>(
        lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.getAllLobbiesUrlPart
    ).body ?: throw LobbyServiceFailException()

    fun getLobbyInfoForStart(userId: Long) = restTemplate.getForEntity<GetLobbyInfoForStartResponse>(
        lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.getLobbyForStartUrlPart + "?userId=${userId}"
    ).body ?: throw LobbyServiceFailException()

    fun sendInfoAboutGameSession(playerInfos: Collection<PlayerInfo>) =
        restTemplate.postForEntity<SendGameSessionConnectionInfoResponse>(
            lobbyServiceConfiguration.lobbyServiceUrl + lobbyServiceConfiguration.sendGameSessionConnectionInfoUrlPart,
            SendGameSessionConnectionInfoRequest(playerInfos)
        ).body ?: throw LobbyServiceFailException()
}