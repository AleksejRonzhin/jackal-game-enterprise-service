package ru.rsreu.jackal.security.refresh.dto

import ru.rsreu.jackal.shared_models.ResponseBody

data class RefreshResponse(
    val accessToken: String = "",
    val refreshToken: String = "",
    override val responseStatus: RefreshResponseStatus
) : ResponseBody<RefreshResponseStatus>
