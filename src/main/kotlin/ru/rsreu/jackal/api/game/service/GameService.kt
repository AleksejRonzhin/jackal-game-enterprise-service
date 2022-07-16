package ru.rsreu.jackal.api.game.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.game.Game
import ru.rsreu.jackal.api.game.GameMode
import ru.rsreu.jackal.api.game.dto.GameInfo
import ru.rsreu.jackal.api.game.dto.GameModeInfo
import ru.rsreu.jackal.api.game.exception.GameNotFoundException
import ru.rsreu.jackal.api.game.repository.GameModeRepository
import ru.rsreu.jackal.api.game.repository.GameRepository

@Service
class GameService(private val gameRepository: GameRepository, private val gameModeRepository: GameModeRepository) {
    fun checkGameIsExistsOrThrow(gameId: Long) {
        if (gameRepository.existsById(gameId)) {
            throw GameNotFoundException()
        }
    }

    fun getByIdOrThrow(gameId: Long): GameMode =
        gameModeRepository.findById(gameId).orElseThrow { GameNotFoundException() }

    fun registerGame(title: String, serviceAddress: String, clientAddress: String, modes: List<GameModeInfo>) {
        val game = Game(title = title, serviceAddress = serviceAddress, clientAddress = clientAddress)
        // TODO added modes
        gameRepository.save(game)
    }

    fun getAllGameInfo(): List<GameInfo> = gameRepository.findAll().map { transformGameToGameInfo(it) }

    private fun transformGameToGameInfo(game: Game): GameInfo {
        val modesInfo = listOf<GameModeInfo>()
        // TODO get modes
        return GameInfo(game.id!!, game.title, modesInfo)
    }
}