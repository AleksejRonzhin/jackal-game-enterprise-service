package ru.rsreu.jackal.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rsreu.jackal.models.User
import ru.rsreu.jackal.services.UserService

@RestController
@RequestMapping("api/users")
class UserController(val userService: UserService) {

    @GetMapping("/test")
    fun test(): User? {
        return userService.create()
    }
}