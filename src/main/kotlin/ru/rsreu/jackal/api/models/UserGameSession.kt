package ru.rsreu.jackal.api.models

import javax.persistence.*

@Entity
@Table(name = "user_game_sessions", schema = "public")
class UserGameSession(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @ManyToOne
    val user: User,

    @ManyToOne
    val gameSession: GameSession,

    val statisticsJSON: String
)