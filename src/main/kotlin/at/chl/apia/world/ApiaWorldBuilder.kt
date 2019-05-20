package at.chl.apia.world

import at.chl.apia.GameConfig
import at.chl.apia.attributes.vector.Vector
import at.chl.apia.blocks.GameBlock
import at.chl.apia.builders.GameBlockFactory
import at.chl.apia.extensions.sameLevelNeighborsShuffled
import at.chl.apia.random.Random
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import kotlin.math.roundToInt

class ApiaWorldBuilder(private val worldSize: Size3D) {
    private val width = worldSize.xLength
    private val depth = worldSize.yLength

    private val logger = LoggerFactory.getLogger("at.chl.apia.world.ApiaWorldBuilder")

    private var startingPosition = Position3D.unknown()
    private var blocks: MutableMap<Position3D, GameBlock> = mutableMapOf()
    private var targetPosition = Position3D.unknown()



    fun build(visibleSize: Size3D): World = World(blocks, visibleSize, worldSize, startingPosition)

    fun makeApia(): ApiaWorldBuilder {
        return createPath().smooth(GameConfig.PATH_SMOOTHING).makeExit()
    }

    private fun makeExit(): ApiaWorldBuilder {
        blocks[targetPosition] = GameBlockFactory.exitBlock()
        return this
    }

    private fun createPath(): ApiaWorldBuilder {
        forAllPositions { pos ->
            blocks[pos] = GameBlockFactory.wall()
        }

        startingPosition = this.createRandomOnEdgePosition(width, depth - 1, GameConfig.PATH_WIDTH_MIN)
        logger.info("Starting position: $startingPosition")
        blocks[startingPosition] = GameBlockFactory.floor()

        targetPosition = this.createRandomOnEdgePosition(width, 0,  GameConfig.PATH_WIDTH_MIN)
        logger.info("Target position: $targetPosition")
//        blocks[targetPosition] = GameBlockFactory.floor()

        val overallDirection = Vector.create(targetPosition, startingPosition)
        logger.info("directecion: $overallDirection")

        val overallNormalizedDirection = overallDirection.normalize()
        logger.info("normalized direction: $overallNormalizedDirection")

        var currentPosition = startingPosition
        var currentDirection = overallNormalizedDirection


        var currentY : Int = startingPosition.y
        while (currentY >= 0){

            buildFloorRow(currentPosition)
            currentDirection = Vector.create(targetPosition, currentPosition).normalize()
            currentPosition = Position3D.create(currentPosition.x + currentDirection.x.roundToInt(),
                currentPosition.y + currentDirection.y.roundToInt(), 0)

            currentY--
        }



        return this
    }

    private fun buildFloorRow(position: Position3D) {
        val radius = (Random.randomInt(GameConfig.PATH_WIDTH_MIN, GameConfig.PATH_WIDTH_MAX) - 1) / 2

        for (x in position.x - radius .. position.x + radius){
            blocks[Position3D.create(x, position.y, 0)] = GameBlockFactory.floor()
        }
    }

    private fun smooth(iterations: Int): ApiaWorldBuilder {
        val newBlocks = mutableMapOf<Position3D, GameBlock>()
        repeat(iterations) {
            forAllPositions { pos ->
                val (x, y, z) = pos
                var floors = 0
                var rocks = 0
                pos.sameLevelNeighborsShuffled().plus(pos).forEach { neighbor ->
                    blocks.whenPresent(neighbor) { block ->
                        if (block.isEmptyFloor) {
                            floors++
                        } else rocks++
                    }
                }
                newBlocks[Positions.create3DPosition(x, y, z)] = if (floors >= rocks) GameBlockFactory.floor() else GameBlockFactory.wall()
            }
            blocks = newBlocks
        }
        return this
    }

    private fun createRandomOnEdgePosition(range: Int, edge: Int, offset: Int): Position3D {

        return Position3D.create(offset+Random.randomInt(range-(2*offset)),edge,0)
    }



    private fun forAllPositions(fn: (Position3D) -> Unit) {
        worldSize.fetchPositions().forEach(fn)
    }
    private fun MutableMap<Position3D, GameBlock>.whenPresent(pos: Position3D, fn: (GameBlock) -> Unit) {
        this[pos]?.let(fn)
    }
}