package ru.rsreu.jackal.api

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.game.service.GameService
import ru.rsreu.jackal.api.lobby.dto.ClientLobbyInfo
import ru.rsreu.jackal.api.lobby.dto.ClientLobbyMemberInfo
import ru.rsreu.jackal.api.user.User
import ru.rsreu.jackal.api.user.service.UserService
import ru.rsreu.jackal.shared_models.LobbyInfo
import ru.rsreu.jackal.shared_models.LobbyMemberInfo

@Service
class TransformResponseService(val gameService: GameService, val userService: UserService) {

    fun transformLobbiesForClient(lobbies: Collection<LobbyInfo>): Collection<ClientLobbyInfo> =
        lobbies.map { transformLobbyToClientLobby(it) }

    private fun transformLobbyToClientLobby(lobbiesInfo: LobbyInfo): ClientLobbyInfo {
        return ClientLobbyInfo(
            lobbiesInfo.title, lobbiesInfo.isPublic, lobbiesInfo.members.map { getClientUserInfo(it) }, null
        )
    }

    private fun getClientUserInfo(lobbyMemberInfo: LobbyMemberInfo): ClientLobbyMemberInfo {
        val user = userService.getUserById(lobbyMemberInfo.userId)
        return ClientLobbyMemberInfo(
            user.get().name,
            user.get().pictureUrl,
            lobbyMemberInfo.status,
            lobbyMemberInfo.isHost
        )
    }
}