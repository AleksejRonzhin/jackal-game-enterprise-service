package ru.rsreu.jackal.security.refresh.repository

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.security.refresh.model.RefreshToken

interface RefreshTokenRepository : CrudRepository<RefreshToken, Long> {
    fun deleteByValue(value: String): Long
}