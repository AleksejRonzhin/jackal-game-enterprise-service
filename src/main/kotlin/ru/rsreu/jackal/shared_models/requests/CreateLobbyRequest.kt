package ru.rsreu.jackal.shared_models.requests

data class CreateLobbyRequest(
    val lobbyName: String,
    val isPrivateLobby: Boolean,
    val lobbyPassword: String?,
    var hostId: Long?
)
