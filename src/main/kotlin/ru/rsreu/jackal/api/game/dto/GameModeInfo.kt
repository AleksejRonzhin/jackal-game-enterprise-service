package ru.rsreu.jackal.api.game.dto

data class GameModeInfo(
    val id: Long? = null,
    val title: String,
    val minPlayerNumber: Int,
    val maxPlayerNumber: Int
)