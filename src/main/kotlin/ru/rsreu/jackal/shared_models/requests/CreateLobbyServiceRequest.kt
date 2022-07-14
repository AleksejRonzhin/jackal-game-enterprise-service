package ru.rsreu.jackal.shared_models.requests

data class CreateLobbyServiceRequest(
    val lobbyName: String,
    val lobbyPassword: String?,
    var hostId: Long
)