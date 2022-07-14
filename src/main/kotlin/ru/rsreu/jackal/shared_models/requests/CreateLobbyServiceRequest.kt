package ru.rsreu.jackal.shared_models.requests

data class CreateLobbyServiceRequest(
    val lobbyTitle: String,
    val lobbyPassword: String?,
    var hostId: Long
)