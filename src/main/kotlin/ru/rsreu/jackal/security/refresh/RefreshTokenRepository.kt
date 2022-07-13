package ru.rsreu.jackal.security.refresh

import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, Long>