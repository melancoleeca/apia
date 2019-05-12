package at.chl.apia.attributes.types

import at.chl.apia.attributes.CombatStats
import at.chl.apia.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface Combatant : EntityType

val GameEntity<Combatant>.combatStats: CombatStats
    get() = findAttribute(CombatStats::class).get()
