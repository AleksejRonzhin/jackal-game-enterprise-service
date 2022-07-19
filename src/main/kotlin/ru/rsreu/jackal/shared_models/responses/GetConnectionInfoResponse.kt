package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.WebSocketInfo

data class GetConnectionInfoResponse(
    val webSocketInfo: WebSocketInfo? = null,
    val token: String? = null,
    override val responseStatus: HttpResponseStatus
) : HttpResponse(responseStatus)