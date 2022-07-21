package ru.rsreu.jackal.api.game.service

import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity
import ru.rsreu.jackal.api.game.exception.GameServiceFailException
import ru.rsreu.jackal.api.game.exception.GameServiceNotAvailableException

class GameHttpSender(val restTemplate: RestTemplate) {
    inline fun <reified T> sendPostToApiUrl(fullApiUrl: String, body: Any?): T =
        restTemplate.runCatching { postForEntity<T>(fullApiUrl, body) }
            .onFailure { throw GameServiceNotAvailableException() }.getOrThrow().body
            ?: throw GameServiceFailException()

    inline fun <reified T> sendGetToApiUrl(fullApiUrl: String): T =
        restTemplate.runCatching { getForEntity<T>(fullApiUrl) }.onFailure { throw GameServiceNotAvailableException() }
            .getOrThrow().body ?: throw GameServiceFailException()
}