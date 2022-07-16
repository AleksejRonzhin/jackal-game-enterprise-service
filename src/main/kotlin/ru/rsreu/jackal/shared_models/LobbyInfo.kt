package ru.rsreu.jackal.shared_models

data class LobbyInfo(
    val title: String,
    val isPublic: Boolean,
    val members: List<LobbyMemberInfo>,
    val hostId: Long,
    val gameId: Long?
)