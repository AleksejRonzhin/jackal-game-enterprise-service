package ru.rsreu.jackal.models

import java.util.*
import javax.persistence.*

@Entity
@Table(name="GAME_SESSIONS")
class GameSession (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne
    val gameMode: GameMode,

    val startDate: Date,

    val endDate: Date,

    @Enumerated(EnumType.ORDINAL)
    val sessionStatus: GameSessionStatus
    )