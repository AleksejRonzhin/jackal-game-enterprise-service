package ru.rsreu.jackal.api.user.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.api.user.dto.GetUserInfoResponse
import ru.rsreu.jackal.api.user.dto.GetUserInfoStatus
import ru.rsreu.jackal.api.user.service.UserService

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {
    @GetMapping("/info")
    fun getUserInfo(@RequestParam userId: Long): ResponseEntity<GetUserInfoResponse> {
        val user = userService.getUserByIdOrThrow(userId)
        return ResponseEntity.ok(
            GetUserInfoResponse(user.name, user.pictureUrl, GetUserInfoStatus.OK)
        )
    }
}