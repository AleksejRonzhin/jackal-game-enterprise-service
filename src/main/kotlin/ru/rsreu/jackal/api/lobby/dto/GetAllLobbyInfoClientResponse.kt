package ru.rsreu.jackal.api.lobby.dto

import ru.rsreu.jackal.shared_models.ResponseBody

data class GetAllLobbyInfoClientResponse(
    val lobbiesInfo: Collection<ClientLobbyInfo>,
    override val responseStatus: GetAllLobbyInfoClientStatus
): ResponseBody<GetAllLobbyInfoClientStatus>

enum class GetAllLobbyInfoClientStatus{
    OK
}