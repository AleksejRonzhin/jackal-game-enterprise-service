package ru.rsreu.jackal.shared_models.requests

import java.util.*

data class SendGameSessionConnectionInfoRequest(
    val lobbyId: UUID
)
