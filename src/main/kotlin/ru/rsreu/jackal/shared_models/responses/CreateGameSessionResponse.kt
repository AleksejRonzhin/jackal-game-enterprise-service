package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import java.util.*

data class CreateGameSessionResponse(
    val sessionId: UUID,
    val startDate: Date,
    override val responseStatus: HttpResponseStatus
) : HttpResponse(responseStatus)