package ru.rsreu.jackal.api.lobby.dto

data class CreateLobbyClientRequest(
    val lobbyTitle: String,
    val lobbyPassword: String?
)
