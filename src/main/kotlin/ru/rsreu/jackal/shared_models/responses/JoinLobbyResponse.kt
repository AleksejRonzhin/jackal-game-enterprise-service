package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.ResponseBody
import ru.rsreu.jackal.shared_models.WebSocketInfo

data class JoinLobbyResponse(
    val webSocketInfo: WebSocketInfo? = null,
    val token: String? = null,
    override val responseStatus: JoinLobbyStatus
) : ResponseBody<JoinLobbyStatus>

enum class JoinLobbyStatus {
    OK, USER_ALREADY_IN_LOBBY, LOBBY_NOT_FOUND, WRONG_PASSWORD, USER_IN_LOBBY_BLACK_LIST
}
