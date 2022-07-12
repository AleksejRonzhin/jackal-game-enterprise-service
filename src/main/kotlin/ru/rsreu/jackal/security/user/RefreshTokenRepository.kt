package ru.rsreu.jackal.security.user

import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, Long> {

}