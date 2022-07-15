package ru.rsreu.jackal.api.game

import javax.persistence.*

@Entity
@Table(name = "games", schema = "public")
class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val serviceAddress: String,

    val clientAddress: String,

    val maxPLayerNumber: Int
)