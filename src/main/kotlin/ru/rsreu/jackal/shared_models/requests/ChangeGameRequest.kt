package ru.rsreu.jackal.shared_models.requests

data class ChangeGameRequest(
    val gameModeId: Long, val userId: Long
)