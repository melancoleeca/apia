package at.chl.apia.commands

import at.chl.apia.extensions.GameCommand
import at.chl.apia.extensions.GameEntity
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.impl.Position3D

data class MoveCamera(override val context: GameContext,
                      override val source: GameEntity<EntityType>,
                      val previousPosition: Position3D) : GameCommand<EntityType>
