package ru.rsreu.jackal.security.refresh.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.security.jwt.JwtTokenProvider

@Service
class RefreshService(val jwtTokenProvider: JwtTokenProvider) {
    fun getNewTokensPair(accessToken: String, refreshToken: String): Pair<String, String> {
        val accessFromRefresh = jwtTokenProvider.getJwtFromRefresh(refreshToken)
        if (accessFromRefresh == accessToken) {

        }
        return Pair("", "")
    }
}