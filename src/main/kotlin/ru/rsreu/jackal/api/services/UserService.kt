package ru.rsreu.jackal.api.services

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.models.Role
import ru.rsreu.jackal.api.models.User
import ru.rsreu.jackal.api.repositories.UserRepository

@Service
class UserService(private val repo: UserRepository) {
    fun create(username: String): User {
        return repo.save(
            User(name = username, role = Role.USER))
    }
}