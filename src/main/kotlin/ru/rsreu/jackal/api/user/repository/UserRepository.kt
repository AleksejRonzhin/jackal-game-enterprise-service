package ru.rsreu.jackal.api.user.repository

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.api.user.User

interface UserRepository : CrudRepository<User, Long>