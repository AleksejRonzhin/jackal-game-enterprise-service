package ru.rsreu.jackal.security.refresh

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.security.refresh.dto.RefreshRequest
import ru.rsreu.jackal.security.refresh.dto.RefreshResponse
import ru.rsreu.jackal.security.refresh.dto.RefreshResponseStatus

@RestController
class RefreshController {

    fun refreshToken(@RequestBody body: RefreshRequest): ResponseEntity<RefreshResponse> {
        return ResponseEntity.ok(RefreshResponse("", "", RefreshResponseStatus.OK))
    }
}