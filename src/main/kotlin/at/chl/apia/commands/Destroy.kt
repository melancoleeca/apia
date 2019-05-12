package at.chl.apia.commands

import at.chl.apia.extensions.GameCommand
import at.chl.apia.extensions.GameEntity
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Destroy(override val context: GameContext,
                   override val source: GameEntity<EntityType>,
                   val target: GameEntity<EntityType>,
                   val cause: String = "natural causes.") : GameCommand<EntityType>
