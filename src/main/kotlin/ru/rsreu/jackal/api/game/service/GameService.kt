package ru.rsreu.jackal.api.game.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import ru.rsreu.jackal.api.game.Game
import ru.rsreu.jackal.api.game.GameMode
import ru.rsreu.jackal.api.game.GameSession
import ru.rsreu.jackal.api.game.UserGameSession
import ru.rsreu.jackal.api.game.dto.GameModeInfo
import ru.rsreu.jackal.api.game.exception.GameModeNotFoundException
import ru.rsreu.jackal.api.game.repository.GameModeRepository
import ru.rsreu.jackal.api.game.repository.GameRepository
import ru.rsreu.jackal.api.game.repository.GameSessionRepository
import ru.rsreu.jackal.api.game.repository.UserGameSessionRepository
import ru.rsreu.jackal.api.user.User
import ru.rsreu.jackal.shared_models.responses.CreateGameSessionResponse
import java.util.*

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val gameModeRepository: GameModeRepository,
    private val gameSessionRepository: GameSessionRepository,
    private val userGameSessionRepository: UserGameSessionRepository,
    private val restTemplate: RestTemplate
) {
    fun checkGameIsExistsOrThrow(gameModeId: Long) {
        if (!gameModeRepository.existsById(gameModeId)) {
            throw GameModeNotFoundException()
        }
    }

    fun getGameModeByIdOrThrow(gameModeId: Long): GameMode {
        return gameModeRepository.findById(gameModeId).orElseThrow { throw GameModeNotFoundException() }
    }

    fun getGameByGameModeId(gameModeId: Long): Game = getGameModeByIdOrThrow(gameModeId).game

    fun registerGame(title: String, serviceAddress: String, clientAddress: String, modes: Collection<GameModeInfo>) {
        val game = Game(title = title, serviceAddress = serviceAddress, clientAddress = clientAddress)
        gameRepository.save(game)
        modes.forEach {
            gameModeRepository.save(
                GameMode(
                    game = game,
                    title = it.title,
                    maxPlayerNumber = it.maxPlayerNumber,
                    minPlayerNumber = it.minPlayerNumber
                )
            )
        }
    }

    fun getAllGame(): MutableIterable<Game> = gameRepository.findAll()

    fun sendCreateGameSessionCreate(game: Game, userIds: Collection<Long>): CreateGameSessionResponse {
        return restTemplate.postForEntity<CreateGameSessionResponse>("").body ?: throw Exception()
    }

    fun createGameSession(users: Collection<User>, startDate: Date, gameMode: GameMode) {
        val gameSession = GameSession(gameMode = gameMode, startDate = startDate)
        gameSessionRepository.save(gameSession)
        users.forEach {
            val userGameSession = UserGameSession(user = it, gameSession = gameSession)
            userGameSessionRepository.save(userGameSession)
        }
    }
}