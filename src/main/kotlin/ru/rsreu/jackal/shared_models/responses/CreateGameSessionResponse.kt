package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.WebSocketInfo
import java.util.*

data class CreateGameSessionResponse(
    val playerInfos: Collection<PlayerInfo>,
    val startDate: Date,
    override val responseStatus: HttpLobbyResponseStatus
) : HttpLobbyResponse(responseStatus)

data class PlayerInfo(
    val userId: Long, val jwt: String, val webSocketInfo: WebSocketInfo
)