package at.chl.apia.commands

import at.chl.apia.extensions.GameCommand
import at.chl.apia.extensions.GameEntity
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

/**
 * A [GameCommand] representing moving [source] down.
 */
data class MoveDown(override val context: GameContext,
                    override val source: GameEntity<EntityType>) : GameCommand<EntityType>
