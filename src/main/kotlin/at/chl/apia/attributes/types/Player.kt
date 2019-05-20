package at.chl.apia.attributes.types

import at.chl.apia.attributes.PlayerStats
import at.chl.apia.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface PlayerType : EntityType

val GameEntity<Combatant>.playerStats: PlayerStats
    get() = findAttribute(PlayerStats::class).get()
