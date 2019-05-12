package at.chl.apia.world

import at.chl.apia.GameConfig
import at.chl.apia.GameConfig.WORLD_SIZE
import at.chl.apia.attributes.types.Player
import at.chl.apia.builders.EntityFactory
import at.chl.apia.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.impl.Size3D

class GameBuilder(val worldSize: Size3D = WORLD_SIZE) {

    private val visibleSize = Sizes.create3DSize(
            xLength = GameConfig.WINDOW_WIDTH - GameConfig.SIDEBAR_WIDTH,
            yLength = GameConfig.WINDOW_HEIGHT - GameConfig.LOG_AREA_HEIGHT,
            zLength = 1)

    val world = ApiaWorldBuilder(worldSize)
            .makeApia()
            .build(visibleSize = visibleSize)

    fun buildGame(): Game {

        prepareWorld()

        val player = addPlayer()
        addFungi()

        return Game.create(
                player = player,
                world = world)
    }

    private fun prepareWorld() = also {
        world.scrollUpBy(world.actualSize().zLength)
    }

    private fun addPlayer(): GameEntity<Player> {
        return EntityFactory.newPlayer().addToWorldOnStartingPosition()
    }

    private fun addFungi() = also {
        repeat(world.actualSize().zLength) { level ->
            repeat(GameConfig.FUNGI_PER_LEVEL) {
                EntityFactory.newFungus().addToWorld(level)
            }
        }
    }

    private fun <T : EntityType> GameEntity<T>.addToWorld(
            atLevel: Int,
            atArea: Size = world.actualSize().to2DSize()): GameEntity<T> {
        world.addAtEmptyPosition(this,
                offset = Positions.default3DPosition().withZ(atLevel),
                size = Size3D.from2DSize(atArea))
        return this
    }

    private fun <T : EntityType> GameEntity<T>.addToWorldOnStartingPosition(): GameEntity<T> {
        world.addAtStartingPosition(this)
        return this
    }

    companion object {

        fun defaultGame() = GameBuilder(
                worldSize = GameConfig.WORLD_SIZE).buildGame()
    }
}
