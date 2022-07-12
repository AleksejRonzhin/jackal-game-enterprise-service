package ru.rsreu.jackal.api.repositories

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.api.models.GameMode

interface GameModeRepository : CrudRepository<GameMode, Long>