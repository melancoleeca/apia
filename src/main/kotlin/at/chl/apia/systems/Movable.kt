package at.chl.apia.systems

import at.chl.apia.attributes.types.Player
import at.chl.apia.commands.MoveCamera
import at.chl.apia.commands.MoveTo
import at.chl.apia.extensions.GameCommand
import at.chl.apia.extensions.position
import at.chl.apia.extensions.tryActionsOn
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.CommandResponse
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.extensions.map

object Movable : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs(MoveTo::class) { (context, entity, position) ->
        val world = context.world
        val previousPosition = entity.position      // 1
        var result: Response = Pass
        world.fetchBlockAt(position).map { block ->
            if (block.isOccupied) {
                result = entity.tryActionsOn(context, block.occupier.get())
            } else {
                if (world.moveEntity(entity, position)) {
                    result = Consumed
                    if (entity.type == Player) {
                        result = CommandResponse(
                            MoveCamera(
                                context = context,
                                source = entity,
                                previousPosition = previousPosition)
                        )
                    }
                }
            }
        }
        result
    }
}
