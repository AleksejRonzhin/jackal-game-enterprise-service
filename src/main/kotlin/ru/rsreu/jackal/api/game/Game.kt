package ru.rsreu.jackal.api.game

import javax.persistence.*

@Entity
@Table(name = "games", schema = "public")
class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    val title: String,

    val serviceAddress: String,

    val clientAddress: String
)