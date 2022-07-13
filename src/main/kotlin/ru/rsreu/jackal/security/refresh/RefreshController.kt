package ru.rsreu.jackal.security.refresh

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.security.refresh.dto.RefreshRequest
import ru.rsreu.jackal.security.refresh.dto.RefreshResponse
import ru.rsreu.jackal.security.refresh.dto.RefreshResponseStatus
import ru.rsreu.jackal.security.refresh.service.RefreshAccessTokenService

@RestController
@RequestMapping("/api/refresh")
class RefreshController(private val refreshAccessTokenService: RefreshAccessTokenService) {

    @PostMapping
    fun refreshToken(@RequestBody body: RefreshRequest): ResponseEntity<RefreshResponse> {
        val (access, refresh) = refreshAccessTokenService.getNewTokensPair(body.accessToken, body.refreshToken)
        return ResponseEntity.ok(RefreshResponse(access, refresh, RefreshResponseStatus.OK))
    }
}