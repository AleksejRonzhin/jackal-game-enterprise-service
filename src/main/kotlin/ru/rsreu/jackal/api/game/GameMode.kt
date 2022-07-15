package ru.rsreu.jackal.api.game

import javax.persistence.*

@Entity
@Table(name = "game_modes", schema = "public")
class GameMode(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    val game: Game,

    val title: String,

    val maxPlayerNumber: Int
)