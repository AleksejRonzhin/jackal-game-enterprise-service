package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.ResponseBody
import ru.rsreu.jackal.shared_models.WebSocketInfo

data class CreateLobbyResponse(
    val webSocketInfo: WebSocketInfo,
    val hostToken: String,
    val lobbySecretKey: String?,
    override val responseStatus: CreateLobbyStatus
) : ResponseBody<CreateLobbyStatus>

enum class CreateLobbyStatus {
    OK, FAIL
}
