package ru.rsreu.jackal.security.user

import org.springframework.data.repository.CrudRepository

interface AuthenticationProviderUserRepository : CrudRepository<AuthenticationProviderUser, Long> {
    fun findByAuthenticationPrincipal(authenticationPrincipal: String): AuthenticationProviderUser?

    fun findByUserName(userName: String): AuthenticationProviderUser
}