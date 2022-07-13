package ru.rsreu.jackal.api.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rsreu.jackal.api.models.GameMode

@Repository
interface GameModeRepository : CrudRepository<GameMode, Long>