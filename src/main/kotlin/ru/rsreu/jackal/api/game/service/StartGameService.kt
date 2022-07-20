package ru.rsreu.jackal.api.game.service

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.game.GameMode
import ru.rsreu.jackal.api.game.exception.UsersInLobbyTooMuchException
import ru.rsreu.jackal.api.game.exception.UsersInLobbyTooSmallException
import ru.rsreu.jackal.api.lobby.service.LobbyService
import ru.rsreu.jackal.api.user.service.UserService
import ru.rsreu.jackal.shared_models.HttpResponseStatus

@Service
class StartGameService(
    private val lobbyService: LobbyService, private val gameService: GameService, private val userService: UserService
) {

    fun start(userId: Long): HttpResponseStatus {
        val lobbyInfoResponse = lobbyService.getLobbyInfoForStart(userId)
        if (lobbyInfoResponse.responseStatus != HttpResponseStatus.OK) {
            return lobbyInfoResponse.responseStatus
        }

        val gameMode = gameService.getGameModeByIdOrThrow(lobbyInfoResponse.gameModeId!!)
        checkUsersIsEnoughOrThrow(lobbyInfoResponse.userIds!!.size, gameMode)

        val createGameSessionResponse =
            gameService.sendCreateGameSessionCreate(gameMode, lobbyInfoResponse.userIds, lobbyInfoResponse.lobbyId!!)
        if (createGameSessionResponse.responseStatus != HttpResponseStatus.OK) {
            return createGameSessionResponse.responseStatus
        }

        val users = createGameSessionResponse.playerInfos.map { userService.getUserByIdOrThrow(it.userId) }
        gameService.createGameSession(users, createGameSessionResponse.startDate, gameMode)

        val sendInfoResponse = lobbyService.sendInfoAboutGameSession(createGameSessionResponse.playerInfos)
        if (sendInfoResponse.responseStatus != HttpResponseStatus.OK) {
            return sendInfoResponse.responseStatus
        }
        return HttpResponseStatus.OK
    }

    private fun checkUsersIsEnoughOrThrow(usersCount: Int, gameMode: GameMode) {
        if (usersCount < gameMode.minPlayerNumber) {
            throw UsersInLobbyTooSmallException()
        }
        if (usersCount > gameMode.maxPlayerNumber) {
            throw UsersInLobbyTooMuchException()
        }
    }
}