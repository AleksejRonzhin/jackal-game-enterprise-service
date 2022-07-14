package ru.rsreu.jackal.api.controllers.dto

data class CreateLobbyClientRequest(
    val lobbyTitle: String,
    val lobbyPassword: String?
)
