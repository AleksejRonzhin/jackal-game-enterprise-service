package ru.rsreu.jackal.api.repositories

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.api.models.GameSession

interface GameSessionRepository : CrudRepository<GameSession, Long>