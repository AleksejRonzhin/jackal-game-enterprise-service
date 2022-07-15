package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.ResponseBody
import ru.rsreu.jackal.shared_models.WebSocketInfo

data class PreConnectLobbyResponse(
    val webSocketInfo: WebSocketInfo?,
    val token: String?,
    override val responseStatus: PreConnectLobbyStatus
) : ResponseBody<PreConnectLobbyStatus>

enum class PreConnectLobbyStatus {
    OK,
    USER_ALREADY_IN_LOBBY,
    LOBBY_NOT_FOUND,
    WRONG_PASSWORD,
    USER_IN_LOBBY_BLACK_LIST,
    NOT_UNIQUE_LOBBY_TITLE
}