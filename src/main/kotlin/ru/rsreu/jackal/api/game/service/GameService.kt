package ru.rsreu.jackal.api.game.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.game.exception.GameIsNotFoundException
import ru.rsreu.jackal.api.game.repository.GameRepository

@Service
class GameService(private val repo: GameRepository) {
    fun checkGameIsExistsOrThrow(gameId: Long) {
        if (repo.existsById(gameId)) {
            throw GameIsNotFoundException()
        }
    }
}