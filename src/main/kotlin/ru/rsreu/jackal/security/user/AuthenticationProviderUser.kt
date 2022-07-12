package ru.rsreu.jackal.security.user

import ru.rsreu.jackal.api.models.User
import javax.persistence.*

@Entity
@Table(name = "authentication_provider_users", schema = "public")
class AuthenticationProviderUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    val user: User,

    val authenticationPrincipal: String,

    val externalAuthenticationProvider: ExternalAuthenticationProvider
)