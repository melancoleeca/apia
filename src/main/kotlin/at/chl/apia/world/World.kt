package at.chl.apia.world

import at.chl.apia.blocks.GameBlock
import at.chl.apia.builders.GameBlockFactory
import at.chl.apia.extensions.GameEntity
import at.chl.apia.extensions.position
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.Engines
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.extensions.fold
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.UIEvent

class World(
    blocks: Map<Position3D, GameBlock>,
    visibleSize: Size3D,
    actualSize: Size3D,
    startingPosition: Position3D
) : GameArea<Tile, GameBlock> by GameAreaBuilder.newBuilder<Tile, GameBlock>()
        .withVisibleSize(visibleSize)
        .withActualSize(actualSize)
        .withDefaultBlock(DEFAULT_BLOCK)
        .withLayersPerBlock(1)
        .build() {

    private val startingPosition = startingPosition
    private val engine: Engine<GameContext> = Engines.newEngine()

    init {
        blocks.forEach { pos, block ->
            setBlockAt(pos, block)
            block.entities.forEach { entity ->
                engine.addEntity(entity)
                entity.position = pos
            }
        }
    }

    fun update(screen: Screen, uiEvent: UIEvent, game: Game) {
        engine.update(GameContext(
                world = this,
                screen = screen,
                uiEvent = uiEvent,
                player = game.player))
    }

    fun addEntity(entity: Entity<EntityType, GameContext>, position: Position3D) {
        entity.position = position
        engine.addEntity(entity)
        fetchBlockAt(position).map {
            it.addEntity(entity)
        }
    }

    fun moveEntity(entity: GameEntity<EntityType>, position: Position3D): Boolean {
        var success = false
        val oldBlock = fetchBlockAt(entity.position)
        val newBlock = fetchBlockAt(position)

        if (bothBlocksPresent(oldBlock, newBlock)) {
            success = true
            oldBlock.get().removeEntity(entity)
            entity.position = position
            newBlock.get().addEntity(entity)
        }
        return success
    }

    fun removeEntity(entity: Entity<EntityType, GameContext>) {
        fetchBlockAt(entity.position).map {
            it.removeEntity(entity)
        }
        engine.removeEntity(entity)
        entity.position = Position3D.unknown()
    }

    private fun bothBlocksPresent(oldBlock: Maybe<GameBlock>, newBlock: Maybe<GameBlock>) =
            oldBlock.isPresent && newBlock.isPresent

    fun addAtEmptyPosition(entity: GameEntity<EntityType>,
                           offset: Position3D = Positions.default3DPosition(),
                           size: Size3D = actualSize()): Boolean {
        return findEmptyLocationWithin(offset, size).fold(
                whenEmpty = {
                    false
                },
                whenPresent = { location ->
                    addEntity(entity, location)
                    true
                })

    }

    fun addAtStartingPosition(entity: GameEntity<EntityType>){
        addEntity(entity,startingPosition)
    }

    fun findEmptyLocationWithin(offset: Position3D, size: Size3D): Maybe<Position3D> {
        var position = Maybe.empty<Position3D>()
        val maxTries = 10
        var currentTry = 0
        while (position.isPresent.not() && currentTry < maxTries) {
            val pos = Positions.create3DPosition(
                    x = (Math.random() * size.xLength).toInt() + offset.x,
                    y = (Math.random() * size.yLength).toInt() + offset.y,
                    z = (Math.random() * size.zLength).toInt() + offset.z)
            fetchBlockAt(pos).map {
                if (it.isEmptyFloor) {
                    position = Maybe.of(pos)
                }
            }
            currentTry++
        }
        return position
    }

    companion object {

        private val DEFAULT_BLOCK = GameBlockFactory.floor()
    }
}
