package at.chl.apia.commands

import at.chl.apia.extensions.GameEntity
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Dig(override val context: GameContext,
               override val source: GameEntity<EntityType>,
               override val target: GameEntity<EntityType>) : EntityAction<EntityType, EntityType>
