package at.chl.apia.systems

import at.chl.apia.commands.Dig
import at.chl.apia.extensions.GameCommand
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object Diggable : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs(Dig::class) { (context, _, target) ->
        //context.world.removeEntity(target)
        Consumed
    }
}
