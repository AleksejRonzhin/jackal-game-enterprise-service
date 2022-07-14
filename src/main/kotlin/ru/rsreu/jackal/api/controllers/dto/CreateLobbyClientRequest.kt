package ru.rsreu.jackal.api.controllers.dto

data class CreateLobbyClientRequest(
    val lobbyName: String,
    val lobbyPassword: String?
)
