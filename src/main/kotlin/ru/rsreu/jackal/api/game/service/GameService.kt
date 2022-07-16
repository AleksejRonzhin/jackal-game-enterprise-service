package ru.rsreu.jackal.api.game.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.game.Game
import ru.rsreu.jackal.api.game.GameMode
import ru.rsreu.jackal.api.game.dto.GameInfo
import ru.rsreu.jackal.api.game.dto.GameModeInfo
import ru.rsreu.jackal.api.game.exception.GameModeNotFoundException
import ru.rsreu.jackal.api.game.repository.GameModeRepository
import ru.rsreu.jackal.api.game.repository.GameRepository

@Service
class GameService(private val gameRepository: GameRepository, private val gameModeRepository: GameModeRepository) {
    fun checkGameIsExistsOrThrow(gameModeId: Long) {
        if (!gameModeRepository.existsById(gameModeId)) {
            throw GameModeNotFoundException()
        }
    }

    fun getGameModeByIdOrThrow(gameModeId: Long): GameMode? {
        return gameModeRepository.findById(gameModeId).orElse(null)
    }

    fun registerGame(title: String, serviceAddress: String, clientAddress: String, modes: Collection<GameModeInfo>) {
        val game = Game(title = title, serviceAddress = serviceAddress, clientAddress = clientAddress)
        gameRepository.save(game)
        modes.forEach {
            gameModeRepository.save(GameMode(game = game, title = it.title, maxPlayerNumber = it.maxPlayerNumber))
        }
    }

    fun getAllGameInfo(): MutableIterable<Game> = gameRepository.findAll()
}