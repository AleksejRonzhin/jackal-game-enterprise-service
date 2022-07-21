package ru.rsreu.jackal.shared_models

data class PlayerInfo(
    val userId: Long, val jwt: String, val webSocketInfo: WebSocketInfo
)