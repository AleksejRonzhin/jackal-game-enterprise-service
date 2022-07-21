package ru.rsreu.jackal.api.game.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.rsreu.jackal.api.game.GameSessionStatus
import ru.rsreu.jackal.api.game.exception.GameServiceFailException
import ru.rsreu.jackal.api.game.exception.UserNotInAnyGameSessionException
import ru.rsreu.jackal.api.game.repository.UserGameSessionRepository
import ru.rsreu.jackal.configuration.GameServiceConfiguration
import ru.rsreu.jackal.shared_models.HttpResponseStatus
import ru.rsreu.jackal.shared_models.PlayerInfo
import ru.rsreu.jackal.shared_models.responses.ReconnectGameSessionResponse

@Service
class GameConnectionService(
    private val userGameSessionRepository: UserGameSessionRepository,
    restTemplate: RestTemplate,
    private val gameServiceConfiguration: GameServiceConfiguration
) {
    private val sender = GameHttpSender(restTemplate)
    fun getConnectionInfoForUser(userId: Long): PlayerInfo {
        val userGameSession =
            userGameSessionRepository.findUserGameSessionByUserIdAndGameSessionStatus(userId, GameSessionStatus.STARTED)
                ?: throw UserNotInAnyGameSessionException()
        val gameServiceResponse = sender.sendGetToApiUrl<ReconnectGameSessionResponse>(
            userGameSession.gameSession.gameMode.game.serviceAddress
                    + gameServiceConfiguration.getConnectionUrlPartInfo + "?userId=${userId}"
        )
        if (gameServiceResponse.responseStatus != HttpResponseStatus.OK) {
            throw GameServiceFailException()
        }
        return gameServiceResponse.playerInfo
    }
}