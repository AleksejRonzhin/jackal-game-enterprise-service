package ru.rsreu.jackal.api.models

import javax.persistence.*

@Entity
@Table(name = "permissions", schema = "public")
class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val permission: String,
) {
    override fun toString(): String = permission
}
