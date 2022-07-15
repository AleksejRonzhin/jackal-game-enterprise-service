package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.ResponseBody
import ru.rsreu.jackal.shared_models.WebSocketInfo

data class GetLobbyConnectionInfoResponse(
    val webSocketInfo: WebSocketInfo? = null,
    val token: String? = null,
    override val responseStatus: GetLobbyConnectionInfoStatus
) : ResponseBody<GetLobbyConnectionInfoStatus>

enum class GetLobbyConnectionInfoStatus {
    OK, USER_NOT_IN_ANY_LOBBY
}