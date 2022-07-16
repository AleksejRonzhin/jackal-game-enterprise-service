package ru.rsreu.jackal.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class LobbyServiceConfiguration(
    @Value("\${lobby_service.url}") val lobbyServiceUrl: String,

    @Value("\${lobby_service.api.create_lobby_url}") val lobbyCreationUrlPart: String,

    @Value("\${lobby_service.api.join_lobby_url}") val lobbyJoinUrlPart: String,

    @Value("\${lobby_service.api.connection_info_url}") val lobbyConnectionInfoUrlPart: String,

    @Value("\${lobby_service.api.get_all_lobbies}") val getAllLobbiesUrlPart: String,

    @Value("\${lobby_service.api.change_game}") val changeGameUrlPart: String,
)
