package ru.rsreu.jackal.shared_models.requests

import ru.rsreu.jackal.shared_models.responses.PlayerInfo

data class SendGameSessionConnectionInfoRequest(
    val playerInfos: Collection<PlayerInfo>
)
