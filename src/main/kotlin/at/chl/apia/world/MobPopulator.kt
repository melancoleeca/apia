package at.chl.apia.world

import at.chl.apia.GameConfig
import at.chl.apia.builders.EntityFactory
import at.chl.apia.extensions.GameEntity
import at.chl.apia.random.Random
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.data.Size

class MobPopulator {

    private val logger = LoggerFactory.getLogger("at.chl.apia.world.MobPopulator")

    fun populateWorld(world: World, stage: Int) {

        val range = GameConfig.POPULATION_LIMIT - GameConfig.BASE_POPULATION

        val chance : Double = 1.0 - (1.0 / stage)

        val actualRange = range * chance

        val rangeOffset = (actualRange * 0.5) + (0.2 * chance)

        var population = GameConfig.BASE_POPULATION + (Random.randomInt(rangeOffset.toInt(), actualRange.toInt()))

        logger.info("stage=$stage, chance=$chance, range is $rangeOffset - $actualRange | adding $population mobs")

        for (mobIndex in 1..(population)){
            addMob(world, mobIndex, stage)
        }

    }

    private fun addMob(world: World, mobIndex: Int, stage: Int) {
        EntityFactory.newFungus().addToWorld(world, 0)
    }

    private fun <T : EntityType> GameEntity<T>.addToWorld(world: World,
        atLevel: Int,
        atArea: Size = world.actualSize().to2DSize()): GameEntity<T> {
        world.addAtEmptyPosition(this,
            offset = org.hexworks.zircon.api.Positions.default3DPosition().withZ(atLevel),
            size = org.hexworks.zircon.api.data.impl.Size3D.from2DSize(atArea))
        return this
    }

}
