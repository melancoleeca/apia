package at.chl.apia.commands

import at.chl.apia.attributes.types.Combatant
import at.chl.apia.extensions.GameEntity
import at.chl.apia.world.GameContext
import org.hexworks.zircon.api.data.impl.Position3D

data class Attack(override val context: GameContext,
                  override val source: GameEntity<Combatant>,
                  override val target: Position3D
) : EntityAction<Combatant, Combatant>
