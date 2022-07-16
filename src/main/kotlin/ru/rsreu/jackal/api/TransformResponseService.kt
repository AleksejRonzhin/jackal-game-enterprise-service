package ru.rsreu.jackal.api

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.game.service.GameService
import ru.rsreu.jackal.api.lobby.dto.ClientLobbyInfo
import ru.rsreu.jackal.api.lobby.dto.ClientLobbyMemberInfo
import ru.rsreu.jackal.api.user.service.UserService
import ru.rsreu.jackal.shared_models.LobbyInfo
import ru.rsreu.jackal.shared_models.LobbyMemberInfo

@Service
class TransformResponseService(val gameService: GameService, val userService: UserService) {

    fun transformLobbiesForClient(lobbies: Collection<LobbyInfo>): Collection<ClientLobbyInfo> =
        lobbies.map { transformLobbyToClientLobby(it) }

    private fun transformLobbyToClientLobby(lobbyInfo: LobbyInfo): ClientLobbyInfo {
        val gameMode = lobbyInfo.gameId?.let { gameService.getByIdOrThrow(it) }

        return ClientLobbyInfo(
            lobbyInfo.title,
            lobbyInfo.isPublic,
            lobbyInfo.members.map { getClientUserInfo(it, isHost(it, lobbyInfo)) },
            gameMode
        )
    }

    private fun isHost(lobbyMemberInfo: LobbyMemberInfo, lobbyInfo: LobbyInfo): Boolean =
        lobbyInfo.hostId == lobbyMemberInfo.userId

    private fun getClientUserInfo(lobbyMemberInfo: LobbyMemberInfo, isHost: Boolean): ClientLobbyMemberInfo {
        val user = userService.getUserById(lobbyMemberInfo.userId)
        return ClientLobbyMemberInfo(
            user.get().name, user.get().pictureUrl, lobbyMemberInfo.status, isHost
        )
    }
}