package ru.rsreu.jackal.shared_models.requests

data class CreateLobbyRequest(
    val lobbyTitle: String,
    val lobbyPassword: String?,
    var hostId: Long
)
