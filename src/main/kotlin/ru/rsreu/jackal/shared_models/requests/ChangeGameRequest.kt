package ru.rsreu.jackal.shared_models.requests

data class ChangeGameRequest(
    val gameId: Long,
    val userId: Long
)