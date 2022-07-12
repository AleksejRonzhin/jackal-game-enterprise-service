package ru.rsreu.jackal.services

import org.springframework.stereotype.Service
import ru.rsreu.jackal.models.Role
import ru.rsreu.jackal.models.User
import ru.rsreu.jackal.repositories.UserRepository

@Service
class UserService(val repo: UserRepository) {
    fun create(): User? {
        val name = "alekjse"
        val user = User(name = name, role = Role.USER)
        repo.save(user)
        return repo.getByName(name)
    }
}