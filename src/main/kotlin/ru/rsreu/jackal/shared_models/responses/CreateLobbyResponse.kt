package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.ResponseBody
import ru.rsreu.jackal.shared_models.WebSocketInfo

data class CreateLobbyResponse(
    val webSocketInfo: WebSocketInfo? = null,
    val token: String? = null,
    override val responseStatus: CreateLobbyStatus
) : ResponseBody<CreateLobbyStatus>

enum class CreateLobbyStatus {
    OK, HOST_ALREADY_IN_LOBBY, NOT_UNIQUE_LOBBY_TITLE
}
