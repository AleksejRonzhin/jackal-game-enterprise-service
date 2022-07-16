package ru.rsreu.jackal.api.lobby.dto

import ru.rsreu.jackal.api.game.dto.GameModeInfo
import ru.rsreu.jackal.shared_models.LobbyMemberStatus

data class ClientLobbyInfo(
    val title: String,
    val isPublic: Boolean,
    val members: Collection<ClientLobbyMemberInfo>,
    val gameMode: GameModeInfo?
)

data class ClientLobbyMemberInfo(
    val name: String, val pictureUrl: String, val status: LobbyMemberStatus, val isHost: Boolean
)

