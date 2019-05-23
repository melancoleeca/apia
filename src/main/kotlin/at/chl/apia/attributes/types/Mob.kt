package at.chl.apia.attributes.types

import at.chl.apia.attributes.MobStats
import at.chl.apia.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface Mob : EntityType {
    val GameEntity<Mob>.mobStats: MobStats
        get() = findAttribute(at.chl.apia.attributes.MobStats::class).get()
}