package ru.rsreu.jackal.api.repositories

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.api.models.User

interface UserRepository : CrudRepository<User, Long> {
    fun getByName(name: String): User?
}