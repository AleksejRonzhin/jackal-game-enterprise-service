package ru.rsreu.jackal.api

import org.springframework.stereotype.Service
import ru.rsreu.jackal.api.game.Game
import ru.rsreu.jackal.api.game.GameMode
import ru.rsreu.jackal.api.game.dto.GameInfo
import ru.rsreu.jackal.api.game.dto.GameModeInfo
import ru.rsreu.jackal.api.game.repository.GameModeRepository
import ru.rsreu.jackal.api.lobby.dto.ClientLobbyInfo
import ru.rsreu.jackal.api.lobby.dto.ClientLobbyMemberInfo
import ru.rsreu.jackal.api.user.repository.UserRepository
import ru.rsreu.jackal.shared_models.LobbyInfo
import ru.rsreu.jackal.shared_models.LobbyMemberInfo

@Service
class TransformResponseService(
    private val userRepository: UserRepository, private val gameModeRepository: GameModeRepository
) {
    fun transformLobbiesForClient(lobbies: Collection<LobbyInfo>): Collection<ClientLobbyInfo> =
        lobbies.map { transformLobbyToClientLobby(it) }

    private fun transformLobbyToClientLobby(lobbyInfo: LobbyInfo): ClientLobbyInfo {
        val gameMode: GameMode? = lobbyInfo.gameId?.let { gameModeRepository.findById(it) }?.orElse(null)
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
        val user = userRepository.findById(lobbyMemberInfo.userId)
        return ClientLobbyMemberInfo(
            if (user.isEmpty) "" else user.get().name,
            if (user.isEmpty) "" else user.get().pictureUrl,
            lobbyMemberInfo.status,
            isHost
        )
    }

    fun transformGamesForClient(games: MutableIterable<Game>): List<GameInfo> =
        games.map { transformGameToGameInfo(it) }

    private fun transformGameToGameInfo(game: Game): GameInfo {
        val gameModes = gameModeRepository.getByGame(game)
        val modesInfo = gameModes.map { GameModeInfo(it.id, it.title, it.maxPlayerNumber, it.minPlayerNumber) }
        return GameInfo(game.id!!, game.title, modesInfo)
    }
}