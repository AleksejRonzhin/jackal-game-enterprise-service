package ru.rsreu.jackal.api.lobby.service

import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity
import ru.rsreu.jackal.api.lobby.exception.LobbyServiceFailException
import ru.rsreu.jackal.api.lobby.exception.LobbyServiceNotAvailableException
import ru.rsreu.jackal.configuration.LobbyServiceConfiguration

class LobbyHttpSender(
    val restTemplate: RestTemplate, val lobbyServiceConfiguration: LobbyServiceConfiguration
) {
    inline fun <reified T> sendPostToApiUrl(apiUrl: String, body: Any? = null): T =
        restTemplate.runCatching { postForEntity<T>(lobbyServiceConfiguration.lobbyServiceUrl + apiUrl, body) }
            .onFailure { exception -> println(exception); throw if (exception is ResourceAccessException) LobbyServiceNotAvailableException() else LobbyServiceFailException() }
            .getOrThrow().body ?: throw LobbyServiceFailException()


    inline fun <reified T> sendGetToApiUrl(apiUrl: String): T =
        restTemplate.runCatching { getForEntity<T>(lobbyServiceConfiguration.lobbyServiceUrl + apiUrl) }
            .onFailure { exception -> throw if (exception is ResourceAccessException) LobbyServiceNotAvailableException() else LobbyServiceFailException() }
            .getOrThrow().body ?: throw LobbyServiceFailException()
}