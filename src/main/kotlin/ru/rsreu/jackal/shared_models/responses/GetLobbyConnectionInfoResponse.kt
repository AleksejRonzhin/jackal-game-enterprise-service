package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.WebSocketInfo

data class GetLobbyConnectionInfoResponse(
    val webSocketInfo: WebSocketInfo? = null,
    val token: String? = null,
    override val responseStatus: HttpLobbyResponseStatus
) : HttpLobbyResponse(responseStatus)