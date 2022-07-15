package ru.rsreu.jackal.api.user.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.user.Permission
import ru.rsreu.jackal.api.user.User
import ru.rsreu.jackal.api.user.exception.UserNotFoundException
import ru.rsreu.jackal.api.user.repository.UserRepository

@Service
class UserService(private val repo: UserRepository) {

    @Value("\${user.default_picture_url}")
    lateinit var defaultPictureUrl: String

    fun create(username: String): User {
        return repo.save(
            User(
                name = username,
                pictureUrl = defaultPictureUrl,
                permissions = listOf(Permission(1, permission = "USER"), Permission(3, permission = "UNBLOCKED")),
            )
        )
    }

    fun getUserByIdOrThrow(userId: Long): User = repo.findById(userId).orElseThrow { throw UserNotFoundException() }
}