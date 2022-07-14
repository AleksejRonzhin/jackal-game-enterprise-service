package ru.rsreu.jackal.shared_models

import org.springframework.beans.factory.annotation.Value

data class WebSocketInfo(private val lobbyId: Long, private val userId: Long) {
    companion object{
        @Value("\${web_sockets.lobby_pattern}")
        private var subscriptionLobbyPattern: String = ""

        @Value("\${web_sockets.user_pattern}")
        private var subscriptionUserPattern: String = ""
    }

    val subscriptionLobbyUrl: String = subscriptionLobbyPattern + lobbyId.toString()
    val subscriptionUserUrl: String = subscriptionUserPattern + userId.toString()
}