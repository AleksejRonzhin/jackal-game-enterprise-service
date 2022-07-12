package ru.rsreu.jackal.repositories

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.models.UserGameSession

interface UserGameSessionRepository : CrudRepository<UserGameSession, Long>