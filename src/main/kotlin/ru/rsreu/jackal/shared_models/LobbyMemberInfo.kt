package ru.rsreu.jackal.shared_models

data class LobbyMemberInfo(
    val userId: Long,
    var status: LobbyMemberStatus = LobbyMemberStatus.NOT_CONNECTED
)