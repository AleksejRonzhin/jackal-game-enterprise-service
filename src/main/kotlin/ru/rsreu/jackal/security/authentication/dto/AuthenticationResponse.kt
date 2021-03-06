package ru.rsreu.jackal.security.authentication.dto

import ru.rsreu.jackal.shared_models.ResponseBody

data class AuthenticationResponse(
    val accessToken: String = "",
    val refreshToken: String = "",
    override val responseStatus: AuthenticationResponseStatus
) : ResponseBody<AuthenticationResponseStatus>
