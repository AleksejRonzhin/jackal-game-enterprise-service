package ru.rsreu.jackal.api.lobby.dto

data class ClientLobbyInfo(
    val title: String,
    val isPublic: Boolean,
    val members: List<ClientLobbyMemberInfo>,
    val gameMode: String? = null // TODO
)

class ClientLobbyMemberInfo

