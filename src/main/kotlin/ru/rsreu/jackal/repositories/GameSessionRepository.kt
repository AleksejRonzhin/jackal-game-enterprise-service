package ru.rsreu.jackal.repositories

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.models.GameSession

interface GameSessionRepository: CrudRepository<GameSession, Long> {
}