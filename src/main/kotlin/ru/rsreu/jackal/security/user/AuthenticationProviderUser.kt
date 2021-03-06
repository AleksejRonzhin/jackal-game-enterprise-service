package ru.rsreu.jackal.security.user

import ru.rsreu.jackal.api.user.User
import ru.rsreu.jackal.security.authentication.service.provider.ExternalAuthenticationProviderType
import ru.rsreu.jackal.security.refresh.model.RefreshToken
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

    val externalAuthenticationProvider: ExternalAuthenticationProviderType,

    @OneToMany(mappedBy = "id", cascade = [CascadeType.ALL], orphanRemoval = true)
    val refreshTokens: List<RefreshToken>
)