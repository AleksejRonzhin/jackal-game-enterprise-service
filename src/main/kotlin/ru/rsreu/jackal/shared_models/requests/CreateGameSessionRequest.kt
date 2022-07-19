package ru.rsreu.jackal.shared_models.requests

data class CreateGameSessionRequest(
    val lobbyId: String,
    val usersIds: Collection<Long>,
    val gameMode: String
)
