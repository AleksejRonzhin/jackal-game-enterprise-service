package ru.rsreu.jackal.api.models.responses

import ru.rsreu.jackal.api.models.WebSocketInfo

data class CreateLobbyResponse(
    val webSocketInfo: WebSocketInfo,
    val hostToken: String,
    val lobbySecretKey: String?
)
