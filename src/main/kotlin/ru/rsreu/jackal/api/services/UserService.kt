package ru.rsreu.jackal.api.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.exceptions.UserNotFoundException
import ru.rsreu.jackal.api.models.Permission
import ru.rsreu.jackal.api.models.User
import ru.rsreu.jackal.api.repositories.UserRepository

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
    fun getUserByName(userName: String) : User{
        return repo.getByName(userName)?: throw UserNotFoundException()
    }
}