package ru.rsreu.jackal.api.game

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "game_sessions", schema = "public")
class GameSession(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    val gameMode: GameMode,

    val startDate: Date,

    val endDate: Date,

    @Enumerated(EnumType.ORDINAL)
    val sessionStatus: GameSessionStatus
)

enum class GameSessionStatus {
    STARTED, FINISHED, PREMATURELY_ENDED
}