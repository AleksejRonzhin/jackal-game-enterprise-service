package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import java.util.UUID

data class GetInfoForStartResponse(
    val lobbyId: UUID? = null,
    val userIds: Collection<Long>? = null,
    val gameModeId: Long? = null,
    override val responseStatus: HttpResponseStatus
) : HttpResponse(responseStatus)
