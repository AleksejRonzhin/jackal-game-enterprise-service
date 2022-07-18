package ru.rsreu.jackal.shared_models.responses

data class SendGameSessionConnectionInfoResponse(
    override val responseStatus: HttpLobbyResponseStatus
) : HttpLobbyResponse(responseStatus)