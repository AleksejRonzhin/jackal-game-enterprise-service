package ru.rsreu.jackal.shared_models

data class LobbyMemberInfo(
    val userId: Long,
    var status: LobbyMemberStatus = LobbyMemberStatus.NOT_CONNECTED
)

enum class LobbyMemberStatus{
    NOT_CONNECTED, READY, NOT_READY, IN_GAME
}