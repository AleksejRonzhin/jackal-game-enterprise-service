package ru.rsreu.jackal.api.game.dto

data class GameInfo(
    val id: Long,
    val name: String,
    val modes: Collection<GameModeInfo>
    )