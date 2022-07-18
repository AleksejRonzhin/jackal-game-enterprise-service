package ru.rsreu.jackal.shared_models.requests

data class CreateGameSessionRequest(
    val userIds: Collection<Long>,
    val gameModeTitle: String
)
