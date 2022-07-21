package ru.rsreu.jackal.api.game.exception

import ru.rsreu.jackal.shared_models.requests.GameSessionCreationError
import java.util.*

class GameSessionCreationException(val lobbyId: UUID, val error: GameSessionCreationError) : RuntimeException()