package ru.rsreu.jackal.api.game.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.game.Game
import ru.rsreu.jackal.api.game.dto.GameInfo
import ru.rsreu.jackal.api.game.dto.GameModeInfo
import ru.rsreu.jackal.api.game.exception.GameIsNotFoundException
import ru.rsreu.jackal.api.game.repository.GameRepository

@Service
class GameService(private val repo: GameRepository) {
    fun checkGameIsExistsOrThrow(gameId: Long) {
        if (repo.existsById(gameId)) {
            throw GameIsNotFoundException()
        }
    }

    fun registerGame(title: String, serviceAddress: String, clientAddress: String, modes: List<GameModeInfo>) {
        val game = Game(title = title, serviceAddress = serviceAddress, clientAddress = clientAddress)
        // TODO added modes
        repo.save(game)
    }

    fun getAllGameInfo(): List<GameInfo> = repo.findAll().map { transformGameToGameInfo(it) }

    private fun transformGameToGameInfo(game: Game): GameInfo {
        val modesInfo = listOf<GameModeInfo>()
        // TODO get modes
        return GameInfo(game.id!!, game.title, modesInfo)
    }
}