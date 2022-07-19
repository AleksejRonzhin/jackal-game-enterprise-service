package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.HttpResponse
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.LobbyInfo

data class GetAllLobbiesResponse(
    val lobbies: Collection<LobbyInfo>,
    override val responseStatus: HttpResponseStatus
) : HttpResponse(responseStatus)
