package ru.rsreu.jackal.api.game.repository

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.api.game.GameMode

interface GameModeRepository : CrudRepository<GameMode, Long>