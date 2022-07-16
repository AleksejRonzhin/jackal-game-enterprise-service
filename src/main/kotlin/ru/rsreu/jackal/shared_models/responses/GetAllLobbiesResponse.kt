package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.LobbyInfo
import ru.rsreu.jackal.shared_models.ResponseBody

data class GetAllLobbiesResponse(
    val lobbies: Collection<LobbyInfo>,
    override val responseStatus: GetAllLobbiesStatus
) : ResponseBody<GetAllLobbiesStatus>

enum class GetAllLobbiesStatus {
    OK
}
