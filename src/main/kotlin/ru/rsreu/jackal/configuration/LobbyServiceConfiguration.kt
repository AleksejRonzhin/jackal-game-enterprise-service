package ru.rsreu.jackal.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class LobbyServiceConfiguration(
    @Value("\${lobby_service.url}") val lobbyServiceUrl: String,

    @Value("\${lobby_service.api.create_lobby_url}") val createLobbyUrlPart: String,

    @Value("\${lobby_service.api.join_lobby_url}") val joinLobbyUrlPart: String,

    @Value("\${lobby_service.api.connection_info_url}") val getLobbyConnectionInfoUrlPart: String,

    @Value("\${lobby_service.api.get_all_lobbies}") val getAllLobbiesUrlPart: String,

    @Value("\${lobby_service.api.change_game}") val changeGameUrlPart: String,

    @Value("\${lobby_service.api.info_for_start}") val getLobbyForStartUrlPart: String,

    @Value("\${lobby_service.api.send-game-session-connection-info}") val sendGameSessionConnectionInfoUrlPart: String,

    @Value("\${lobby_service.api.send-game-session-creation-error-info}")
    val sendGameSessionCreationErrorInfoUrlPart: String,

    @Value("\${lobby_service.api.send-rejected-game-info}") val sendRejectedGameInfoUrlPart: String,

    @Value("\${lobby_service.api.user-finish-game}") val userFinishGameUrlPart: String,

    @Value("\${lobby_service.api.finish-game}") val finishGameUrlPart: String
)