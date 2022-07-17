package ru.rsreu.jackal.api.game.dto

import ru.rsreu.jackal.shared_models.ResponseBody

data class RegisterGameResponse(
    override val responseStatus: RegisterGameStatus
) : ResponseBody<RegisterGameStatus>

enum class RegisterGameStatus {
    OK
}
