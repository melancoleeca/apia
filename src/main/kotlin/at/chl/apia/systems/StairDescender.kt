package at.chl.apia.systems

import at.chl.apia.attributes.types.StairsDown
import at.chl.apia.blocks.GameBlock
import at.chl.apia.commands.MoveDown
import at.chl.apia.extensions.GameCommand
import at.chl.apia.extensions.position
import at.chl.apia.functions.logGameEvent
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.extensions.map


object StairDescender : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs(MoveDown::class) { (context, player) ->
        val world = context.world
        val playerPos = player.position
        world.fetchBlockAt(playerPos).map { block ->
            if (block.hasStairsDown) {
                logGameEvent("You move down one level...")
                world.moveEntity(player, playerPos.withRelativeZ(-1))
                world.scrollOneDown()
            } else {
                logGameEvent("You search for a trapdoor, but you find nothing.")
            }
        }
        Consumed
    }

    private val GameBlock.hasStairsDown: Boolean
        get() = this.entities.any { it.type == StairsDown }
}
