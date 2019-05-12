package at.chl.apia.commands

import at.chl.apia.attributes.types.Combatant
import at.chl.apia.extensions.GameEntity
import at.chl.apia.world.GameContext

data class Attack(override val context: GameContext,
                  override val source: GameEntity<Combatant>,
                  override val target: GameEntity<Combatant>) : EntityAction<Combatant, Combatant>
