package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.ResponseBody
import ru.rsreu.jackal.shared_models.WebSocketInfo

data class ReconnectLobbyResponse(
    val webSocketInfo: WebSocketInfo? = null,
    val token: String? = null,
    override val responseStatus: ReconnectLobbyStatus
) : ResponseBody<ReconnectLobbyStatus>

enum class ReconnectLobbyStatus {
    OK, USER_NOT_IN_ANY_LOBBY
}