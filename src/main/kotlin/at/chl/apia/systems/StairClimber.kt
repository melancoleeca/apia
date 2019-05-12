package at.chl.apia.systems

import at.chl.apia.attributes.types.StairsUp
import at.chl.apia.blocks.GameBlock
import at.chl.apia.commands.MoveUp
import at.chl.apia.extensions.GameCommand
import at.chl.apia.extensions.position
import at.chl.apia.functions.logGameEvent
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.extensions.map

object StairClimber : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs(MoveUp::class) { (context, player) ->
        val world = context.world
        val playerPos = player.position
        world.fetchBlockAt(playerPos).map { block ->
            if (block.hasStairsUp) {
                logGameEvent("You move up one level...")
                world.moveEntity(player, playerPos.withRelativeZ(1))
                world.scrollOneUp()
            } else {
                logGameEvent("You jump up and try to reach the ceiling. You fail.")
            }
        }
        Consumed
    }

    private val GameBlock.hasStairsUp: Boolean
        get() = this.entities.any { it.type == StairsUp }
}
