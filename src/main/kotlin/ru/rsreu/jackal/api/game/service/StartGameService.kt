package ru.rsreu.jackal.api.game.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.rsreu.jackal.api.game.GameMode
import ru.rsreu.jackal.api.game.GameSessionStatus
import ru.rsreu.jackal.api.game.UserGameSessionStatus
import ru.rsreu.jackal.api.game.exception.*
import ru.rsreu.jackal.api.game.repository.GameSessionRepository
import ru.rsreu.jackal.api.game.repository.UserGameSessionRepository
import ru.rsreu.jackal.api.lobby.service.LobbyHttpSender
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.user.exception.UserNotFoundException
import ru.rsreu.jackal.api.user.service.UserService
import ru.rsreu.jackal.configuration.GameServiceConfiguration
import ru.rsreu.jackal.configuration.LobbyServiceConfiguration
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.requests.CreateGameSessionRequest
import ru.rsreu.jackal.shared_models.requests.GameNotStartedRequest
import ru.rsreu.jackal.shared_models.requests.GameSessionCreationError
import ru.rsreu.jackal.shared_models.responses.CreateGameSessionResponse
import java.util.*

@Service
class StartGameService(
    private val lobbyService: LobbyService,
    private val gameService: GameService,
    private val userService: UserService,
    restTemplate: RestTemplate,
    private val lobbyServiceConfiguration: LobbyServiceConfiguration,
    private val gameServiceConfiguration: GameServiceConfiguration,
    private val userGameSessionRepository: UserGameSessionRepository,
    private val gameSessionRepository: GameSessionRepository
) {
    private val gameSender = GameHttpSender(restTemplate)

    private val lobbySender = LobbyHttpSender(restTemplate, lobbyServiceConfiguration)

    fun sendCreateGameSessionCreate(
        gameMode: GameMode, userIds: Collection<Long>, lobbyId: UUID
    ): CreateGameSessionResponse {
        return gameSender.sendPostToApiUrl(
            gameMode.game.serviceAddress + gameServiceConfiguration.createGameSessionUrlPart,
            CreateGameSessionRequest(lobbyId.toString(), userIds, gameMode.title)
        )
    }

    fun start(userId: Long): HttpResponseStatus {
        val lobbyInfoResponse = lobbyService.getLobbyInfoForStart(userId)
        if (lobbyInfoResponse.responseStatus != HttpResponseStatus.OK) {
            return lobbyInfoResponse.responseStatus
        }
        val lobbyId = lobbyInfoResponse.lobbyId!!
        try {
            val gameMode = gameService.getGameModeByIdOrThrow(lobbyInfoResponse.gameModeId!!)

            //checkUsersIsEnoughOrThrow(lobbyInfoResponse.userIds!!.size, gameMode) //TODO Закомменитровано для тестирования!!!

            val users = lobbyInfoResponse.userIds!!.map { userService.getUserByIdOrThrow(it) }

            val createGameSessionResponse = sendCreateGameSessionCreate(gameMode, lobbyInfoResponse.userIds, lobbyId)
            if (createGameSessionResponse.responseStatus != HttpResponseStatus.OK) {
                return createGameSessionResponse.responseStatus
            }

            gameService.createGameSession(users, createGameSessionResponse.startDate, gameMode)

            val sendInfoResponse = lobbyService.sendInfoAboutGameSession(lobbyId)
            if (sendInfoResponse.responseStatus != HttpResponseStatus.OK) {
                return sendInfoResponse.responseStatus
            }
            return HttpResponseStatus.OK
        } catch (exception: Exception) {
            throw GameSessionCreationException(
                lobbyId, when (exception) {
                    is GameServiceNotAvailableException -> GameSessionCreationError.GAME_SERVICE_NOT_AVAILABLE
                    is GameServiceFailException -> GameSessionCreationError.GAME_SERVICE_FAIL
                    is UsersInLobbyTooSmallException -> GameSessionCreationError.USERS_IN_LOBBY_TOO_SMALL
                    is UsersInLobbyTooMuchException -> GameSessionCreationError.USERS_IN_LOBBY_TOO_MUCH
                    is GameModeNotFoundException -> GameSessionCreationError.GAME_NOT_FOUND
                    is UserNotFoundException -> GameSessionCreationError.ENTERPRISE_FAIL
                    else -> GameSessionCreationError.ENTERPRISE_FAIL
                }
            )
        }
    }

    private fun checkUsersIsEnoughOrThrow(usersCount: Int, gameMode: GameMode) {
        if (usersCount < gameMode.minPlayerNumber) {
            throw UsersInLobbyTooSmallException()
        }
        if (usersCount > gameMode.maxPlayerNumber) {
            throw UsersInLobbyTooMuchException()
        }
    }

    fun rejectGame(notConnectedUserId: Long) {
        val gameSession = userGameSessionRepository.findUserGameSessionByUserIdAndGameSessionStatus(
            notConnectedUserId, GameSessionStatus.STARTED
        )?.gameSession
        gameSession?.sessionStatus = GameSessionStatus.REJECTED
        if (gameSession != null) {
            gameSessionRepository.save(gameSession)
            val userGameSessions = userGameSessionRepository.findByGameSession(gameSession)
            userGameSessions.forEach {
                it.status = UserGameSessionStatus.FINISHED
                userGameSessionRepository.save(it)
            }
        }
    }

    fun sendRejectedGameInfoToLobby(request: GameNotStartedRequest) {
        lobbySender.sendPostToApiUrl<Any>(lobbyServiceConfiguration.sendRejectedGameInfoUrlPart, request)
    }
}