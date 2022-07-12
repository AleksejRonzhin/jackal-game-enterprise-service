package ru.rsreu.jackal.api.models

import javax.persistence.*

@Entity
@Table(name = "game_modes", schema = "public")
class GameMode(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @ManyToOne
    val game: Game,

    val title: String
)