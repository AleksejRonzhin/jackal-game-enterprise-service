package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus

data class ChangeGameResponse(
    override val responseStatus: HttpResponseStatus
) : HttpResponse(responseStatus)