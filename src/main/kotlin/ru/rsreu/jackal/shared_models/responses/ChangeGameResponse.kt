package ru.rsreu.jackal.shared_models.responses

import ru.rsreu.jackal.shared_models.ResponseBody

data class ChangeGameResponse(
    override val responseStatus: ChangeGameStatus
): ResponseBody<ChangeGameStatus>

enum class ChangeGameStatus{
    OK, USER_IS_NOT_FOUND, USER_NOT_IN_ANY_LOBBY, USER_IS_NOT_LOBBY_HOST, GAME_IS_NOT_FOUND
}