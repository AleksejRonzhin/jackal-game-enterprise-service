package ru.rsreu.jackal.api.models

import javax.persistence.*

@Entity
@Table(name = "users", schema = "public")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    val pictureUrl: String,

    @ManyToMany
    val permissions: List<Permission>,
)