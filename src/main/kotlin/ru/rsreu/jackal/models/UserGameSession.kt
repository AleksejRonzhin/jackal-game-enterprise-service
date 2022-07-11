package ru.rsreu.jackal.models

import javax.persistence.*

@Entity
@Table(name = "USER_GAME_SESSIONS")
class UserGameSession(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne
    val user: User,

    @ManyToOne
    val gameSession: GameSession,

    val statisticsJSON: String
)