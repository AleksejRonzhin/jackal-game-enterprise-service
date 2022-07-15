package ru.rsreu.jackal.shared_models.requests

data class JoinLobbyRequest(
    val lobbyTitle: String, val lobbyPassword: String?, val userId: Long
)