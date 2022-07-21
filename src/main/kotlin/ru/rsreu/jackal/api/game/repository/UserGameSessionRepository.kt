package ru.rsreu.jackal.api.game.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.api.game.GameSession
import ru.rsreu.jackal.api.game.GameSessionStatus
import ru.rsreu.jackal.api.game.UserGameSession
import ru.rsreu.jackal.api.user.User

interface UserGameSessionRepository : CrudRepository<UserGameSession, Long> {
    @Query(
        """SELECT userGameSession
                FROM UserGameSession userGameSession 
                WHERE userGameSession.user.id = ?1 and userGameSession.gameSession.sessionStatus = ?2"""
    )
    fun findUserGameSessionByUserIdAndGameSessionStatus(
        userId: Long,
        gameSessionStatus: GameSessionStatus
    ): UserGameSession?

    fun findByGameSession(gameSession: GameSession): Iterable<UserGameSession>

    fun findByUser(user: User): UserGameSession?
}