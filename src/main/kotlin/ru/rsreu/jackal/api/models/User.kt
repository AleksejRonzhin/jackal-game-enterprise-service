package ru.rsreu.jackal.api.models

import javax.persistence.*

@Entity
@Table(name = "users", schema = "public")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    val name: String,

    @Enumerated(EnumType.ORDINAL)
    val role: Role
)

enum class Role {
    USER, ADMIN
}