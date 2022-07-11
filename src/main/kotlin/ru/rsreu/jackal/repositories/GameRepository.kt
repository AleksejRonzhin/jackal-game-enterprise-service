package ru.rsreu.jackal.repositories

import org.springframework.data.repository.CrudRepository
import ru.rsreu.jackal.models.Game

interface GameRepository: CrudRepository<Game, Long> {
}