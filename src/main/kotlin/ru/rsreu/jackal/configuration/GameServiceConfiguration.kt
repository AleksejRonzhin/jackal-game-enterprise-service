package ru.rsreu.jackal.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class GameServiceConfiguration(
    @Value("\${game_service.api.create}") val createGameSessionUrlPart: String
)
