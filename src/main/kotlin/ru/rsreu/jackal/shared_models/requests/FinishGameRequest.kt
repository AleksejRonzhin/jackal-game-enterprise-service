package ru.rsreu.jackal.shared_models.requests

import java.util.*

data class FinishGameRequest(
    val endDate: Date, val statistics: Collection<UserStatistics>, val lobbyId: Long
)

data class UserStatistics(
    val userId: Long, val statisticsJSON: String
)

