package ru.rsreu.jackal.shared_models.requests

data class CreateGameSessionRequest(
    val lobbyId: String,
    val userIds: Collection<Long>,
    val gameModeTitle: String
)
