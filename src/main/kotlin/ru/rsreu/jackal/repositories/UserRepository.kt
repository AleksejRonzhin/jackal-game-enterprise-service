package ru.rsreu.jackal.repositories

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.models.User

interface UserRepository : CrudRepository<User, Long> {
    fun getByName(name: String): User?
}