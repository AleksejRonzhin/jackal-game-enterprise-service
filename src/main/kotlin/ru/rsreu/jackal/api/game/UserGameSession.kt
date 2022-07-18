package ru.rsreu.jackal.api.game

import ru.rsreu.jackal.api.user.User
import javax.persistence.*

@Entity
@Table(name = "user_game_sessions", schema = "public")
class UserGameSession(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    val user: User,

    @ManyToOne
    val gameSession: GameSession,

    val statisticsJSON: String? = null
)