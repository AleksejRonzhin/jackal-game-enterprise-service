package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.PlayerInfo

data class ReconnectGameSessionResponse(
    val playerInfo: PlayerInfo,
    override val responseStatus: HttpResponseStatus
) : HttpResponse(responseStatus)