package ru.rsreu.jackal.models

import javax.persistence.*

@Entity
@Table(name = "GAME_MODES")
class GameMode(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne
    var game: Game,

    var title: String
)