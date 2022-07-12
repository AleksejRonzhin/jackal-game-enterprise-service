package ru.rsreu.jackal.api.models

import javax.persistence.*

@Entity
@Table(name = "games", schema = "public")
class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    val serviceAddress: String,

    val clientAddress: String,

    val maxPLayerNumber: Int
)