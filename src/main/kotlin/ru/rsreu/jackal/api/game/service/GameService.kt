package ru.rsreu.jackal.api.game.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.rsreu.jackal.api.game.*
import ru.rsreu.jackal.api.game.dto.GameModeInfo
import ru.rsreu.jackal.api.game.exception.GameModeNotFoundException
import ru.rsreu.jackal.api.game.repository.GameModeRepository
import ru.rsreu.jackal.api.game.repository.GameRepository
import ru.rsreu.jackal.api.game.repository.GameSessionRepository
import ru.rsreu.jackal.api.game.repository.UserGameSessionRepository
import ru.rsreu.jackal.api.lobby.service.LobbyHttpSender
import ru.rsreu.jackal.api.user.User
import ru.rsreu.jackal.configuration.LobbyServiceConfiguration
import java.util.*

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val gameModeRepository: GameModeRepository,
    private val gameSessionRepository: GameSessionRepository,
    private val userGameSessionRepository: UserGameSessionRepository,
    restTemplate: RestTemplate,
    private val lobbyServiceConfiguration: LobbyServiceConfiguration
) {
    private val lobbySender = LobbyHttpSender(restTemplate, lobbyServiceConfiguration)
    fun checkGameIsExistsOrThrow(gameModeId: Long) {
        if (!gameModeRepository.existsById(gameModeId)) {
            throw GameModeNotFoundException()
        }
    }

    fun getGameModeByIdOrThrow(gameModeId: Long): GameMode {
        return gameModeRepository.findById(gameModeId).orElseThrow { throw GameModeNotFoundException() }
    }

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

    fun createGameSession(users: Collection<User>, startDate: Date, gameMode: GameMode) {
        val gameSession = GameSession(gameMode = gameMode, startDate = startDate)
        gameSessionRepository.save(gameSession)
        users.forEach {
            val userGameSession = UserGameSession(user = it, gameSession = gameSession)
            userGameSessionRepository.save(userGameSession)
        }
    }

    fun userFinish(user: User) {
        val userGameSession = userGameSessionRepository.findByUser(user)
        if (userGameSession != null) {
            userGameSession.status = UserGameSessionStatus.FINISHED
            userGameSessionRepository.save(userGameSession)
        }
    }

    fun sendUserFinishToLobby(userId: Long) {
        lobbySender.sendPostToApiUrl<Any>(lobbyServiceConfiguration.userFinishGameUrlPart + "?userId=$userId")
    }
}