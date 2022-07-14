package ru.rsreu.jackal.api.controllers.dto

data class PreConnectLobbyServiceRequest(val lobbyTitle: String, val lobbyPassword: String?, val userId: Long)
