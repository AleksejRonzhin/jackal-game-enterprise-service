package ru.rsreu.jackal.api.game.dto

data class RegisterGameRequest(
    val title: String, val serviceAddress: String, val clientAddress: String, val modes: Collection<GameModeInfo>
)