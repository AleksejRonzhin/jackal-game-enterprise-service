package ru.rsreu.jackal.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class LobbyServiceConfiguration(
    @Value("\${lobby_service.url}")
    val lobbyServiceUrl: String,

    @Value("\${lobby_service.api.create_lobby_url}")
    val lobbyCreationUrlPart: String,

    @Value("\${lobby_service.api.pre_connect_lobby_url}")
    val lobbyPreConnectUrlPart: String
)
