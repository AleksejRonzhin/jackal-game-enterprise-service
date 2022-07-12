package ru.rsreu.jackal.security.user

import javax.persistence.*

@Entity
@Table(name = "refresh_tokens", schema = "public")
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, length = 512)
    val value: String,

    @ManyToOne
    @JoinColumn(name = "authentication_provider_id")
    val authenticationProviderUser: AuthenticationProviderUser
)