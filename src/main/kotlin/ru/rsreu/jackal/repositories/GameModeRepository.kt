package ru.rsreu.jackal.repositories

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.models.GameMode

interface GameModeRepository: CrudRepository<GameMode, Long> {
}