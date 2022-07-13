package ru.rsreu.jackal.models.responses

import ru.rsreu.jackal.models.WebSocketInfo

data class CreateLobbyResponse(
    val webSocketInfo: WebSocketInfo,
    val hostToken: String,
    val lobbySecretKey: String?
)
