package ru.rsreu.jackal.api.game.dto

import ru.rsreu.jackal.shared_models.ResponseBody

data class GetAllGamesResponse(
    val gamesInfo: List<GameInfo>,
    override val responseStatus: GetAllGamesStatus
) : ResponseBody<GetAllGamesStatus>

enum class GetAllGamesStatus{
    OK
}