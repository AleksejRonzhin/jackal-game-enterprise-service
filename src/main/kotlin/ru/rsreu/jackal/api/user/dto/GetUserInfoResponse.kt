package ru.rsreu.jackal.api.user.dto

import ru.rsreu.jackal.shared_models.ResponseBody

data class GetUserInfoResponse(
    val userName: String? = null, val userPictureUrl: String? = null, override val responseStatus: GetUserInfoStatus
) : ResponseBody<GetUserInfoStatus>

enum class GetUserInfoStatus {
    OK, USER_NOT_FOUND
}