package ru.rsreu.jackal.shared_models.requests

data class GameNotStartedRequest(val lobbyId: String, val notConnectedUserIds: List<Long>)