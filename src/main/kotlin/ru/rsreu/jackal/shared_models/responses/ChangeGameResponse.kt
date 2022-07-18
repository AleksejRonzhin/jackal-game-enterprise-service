package ru.rsreu.jackal.shared_models.responses

data class ChangeGameResponse(
    override val responseStatus: HttpLobbyResponseStatus
) : HttpLobbyResponse(responseStatus)