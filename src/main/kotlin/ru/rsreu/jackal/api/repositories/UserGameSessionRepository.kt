package ru.rsreu.jackal.api.repositories

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.api.models.UserGameSession

interface UserGameSessionRepository : CrudRepository<UserGameSession, Long>