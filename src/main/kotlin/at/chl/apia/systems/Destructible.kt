package at.chl.apia.systems

import at.chl.apia.commands.Destroy
import at.chl.apia.extensions.GameCommand
import at.chl.apia.functions.logGameEvent
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object Destructible : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs(Destroy::class) { (context, attacker, target, cause) ->
        context.world.removeEntity(target)

        logGameEvent("$target dies after receiving $cause.")
        Consumed
    }
}
