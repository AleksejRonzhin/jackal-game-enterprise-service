package ru.rsreu.jackal.api.lobby.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.rsreu.jackal.configuration.LobbyServiceConfiguration
import ru.rsreu.jackal.shared_models.requests.ChangeGameRequest
import ru.rsreu.jackal.shared_models.requests.CreateLobbyRequest
import ru.rsreu.jackal.shared_models.requests.JoinLobbyRequest
import ru.rsreu.jackal.shared_models.requests.SendGameSessionConnectionInfoRequest
import ru.rsreu.jackal.shared_models.responses.*
import java.util.*

@Service
class LobbyService(
    restTemplate: RestTemplate,
    private val lobbyServiceConfiguration: LobbyServiceConfiguration,
) {
    private val sender: LobbyHttpSender = LobbyHttpSender(restTemplate, lobbyServiceConfiguration)

    fun create(lobbyName: String, lobbyPassword: String?, hostId: Long): CreateResponse =
        sender.sendPostToApiUrl(
            lobbyServiceConfiguration.createLobbyUrlPart,
            CreateLobbyRequest(lobbyName, lobbyPassword, hostId)
        )

    fun join(lobbyTitle: String, lobbyPassword: String?, userId: Long): JoinResponse =
        sender.sendPostToApiUrl(
            lobbyServiceConfiguration.joinLobbyUrlPart,
            JoinLobbyRequest(lobbyTitle, lobbyPassword, userId)
        )

    fun getInfoAboutSocketConnection(userId: Long): GetConnectionInfoResponse =
        sender.sendGetToApiUrl(lobbyServiceConfiguration.getLobbyConnectionInfoUrlPart + "?userId=${userId}")

    fun changeGame(gameModeId: Long, userId: Long): ChangeGameResponse =
        sender.sendPostToApiUrl(lobbyServiceConfiguration.changeGameUrlPart, ChangeGameRequest(gameModeId, userId))

    fun getAllLobbiesInfo(): GetAllLobbiesResponse =
        sender.sendGetToApiUrl(lobbyServiceConfiguration.getAllLobbiesUrlPart)

    fun getLobbyInfoForStart(userId: Long): GetInfoForStartResponse =
        sender.sendGetToApiUrl(lobbyServiceConfiguration.getLobbyForStartUrlPart + "?userId=${userId}")

    fun sendInfoAboutGameSession(lobbyId: UUID): SendGameSessionConnectionInfoResponse =
        sender.sendPostToApiUrl(
            lobbyServiceConfiguration.sendGameSessionConnectionInfoUrlPart,
            SendGameSessionConnectionInfoRequest(lobbyId)
        )
}

