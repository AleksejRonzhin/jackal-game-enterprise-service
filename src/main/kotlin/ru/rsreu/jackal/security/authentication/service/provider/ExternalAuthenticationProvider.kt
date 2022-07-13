package ru.rsreu.jackal.security.authentication.service.provider

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

abstract class ExternalAuthenticationProvider(
    protected val httpClient: RestTemplate
) {
    abstract val providerType: ExternalAuthenticationProviderType

    abstract fun getAuthentication(accessToken: String): ExternalAccessTokenAuthentication

    protected fun ResponseEntity<String>.getJsonNode(): JsonNode {
        return ObjectMapper().readTree(body)
    }

    protected fun JsonNode.getStringByPath(path: String): String = path(path).asText()
}