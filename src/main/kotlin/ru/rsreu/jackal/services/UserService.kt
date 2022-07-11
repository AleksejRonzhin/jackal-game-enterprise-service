package ru.rsreu.jackal.services

import org.springframework.stereotype.Service
import ru.rsreu.jackal.models.User
import ru.rsreu.jackal.repositories.UserRepository

@Service
class UserService(
    var repo: UserRepository
) {
    fun create(): User? {
        val name = "alekjse"
        val user = User(name = name)
        repo.save(user)
        return repo.getByName(name)
    }
}