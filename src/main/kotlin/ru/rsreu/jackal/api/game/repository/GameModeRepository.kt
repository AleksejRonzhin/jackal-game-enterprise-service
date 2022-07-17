package ru.rsreu.jackal.api.game.repository

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.api.game.Game
import ru.rsreu.jackal.api.game.GameMode

interface GameModeRepository : CrudRepository<GameMode, Long> {
    fun getByGame(game: Game): Collection<GameMode>
}