package ru.rsreu.jackal.models

import javax.persistence.*

@Entity
@Table(name = "GAMES")
class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var serviceAddress: String,

    var clientAddress: String,

    var maxPLayerNumber: Int
)