package at.chl.apia.blocks

import at.chl.apia.builders.GameTileRepository
import at.chl.apia.extensions.GameEntity
import at.chl.apia.extensions.occupiesBlock
import at.chl.apia.extensions.tile
import at.chl.apia.world.GameBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BlockBase

class GameBlock(private var defaultTile: Tile = GameTileRepository.FLOOR,
                private val currentEntities: MutableList<GameEntity<EntityType>> = mutableListOf())
    : BlockBase<Tile>() {


    private val logger = LoggerFactory.getLogger("at.chl.apia.world.GameBlock")

    val isFloor: Boolean
        get() = defaultTile == GameTileRepository.FLOOR


    val isWall: Boolean
        get() = defaultTile == GameTileRepository.WALL

    val isExit: Boolean
        get() = defaultTile == GameTileRepository.EXIT

    val isOccupied: Boolean
        get() = occupier.isPresent

    val occupier: Maybe<GameEntity<EntityType>>
        get() = Maybe.ofNullable(currentEntities.firstOrNull { it.occupiesBlock })

    val isEmptyFloor: Boolean
        get() = currentEntities.isEmpty()

    val entities: Iterable<GameEntity<EntityType>>
        get() = currentEntities.toList()

    override val layers: MutableList<Tile>
        get() {
            val entityTiles = currentEntities.map { it.tile }
            val tile = when {
                entityTiles.contains(GameTileRepository.PLAYER) -> GameTileRepository.PLAYER
                entityTiles.isNotEmpty() -> entityTiles.first()
                else -> defaultTile
            }
            return mutableListOf(tile)
        }

    fun addEntity(entity: GameEntity<EntityType>) {
        currentEntities.add(entity)
        if(isExit && entity.name == "player"){
            logger.info("addEntity: $entity")
            GameBuilder.defaultGame.exitMap()
        }
    }

    fun removeEntity(entity: GameEntity<EntityType>) {
        currentEntities.remove(entity)
    }

    override fun fetchSide(side: BlockSide): Tile {
        return GameTileRepository.EMPTY
    }

    companion object {

        fun createWith(entity: GameEntity<EntityType>) = GameBlock(
                currentEntities = mutableListOf(entity))
    }
}
