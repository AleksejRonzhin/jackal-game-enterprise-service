package ru.rsreu.jackal.api.services

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.exceptions.GameIsNotFoundException
import ru.rsreu.jackal.api.repositories.GameRepository

@Service
class GameService(private val repo: GameRepository) {
    fun checkGameIsExistsOrThrow(gameId: Long) {
        if (repo.existsById(gameId)) {
            throw GameIsNotFoundException()
        }
    }
}