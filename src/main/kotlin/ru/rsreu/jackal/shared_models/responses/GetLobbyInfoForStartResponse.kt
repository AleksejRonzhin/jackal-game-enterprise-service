package ru.rsreu.jackal.shared_models.responses

data class GetLobbyInfoForStartResponse(
    val userIds: Collection<Long>, val gameModeId: Long, override val responseStatus: HttpLobbyResponseStatus
) : HttpLobbyResponse(responseStatus)
