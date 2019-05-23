package at.chl.apia.systems

import at.chl.apia.commands.Destroy
import at.chl.apia.commands.Die
import at.chl.apia.extensions.GameCommand
import at.chl.apia.functions.logGameEvent
import at.chl.apia.view.Director
import at.chl.apia.world.GameBuilder
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object Dieable : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs(Die::class) { (context, attacker, target, cause) ->

        logGameEvent("Game Over")
        GameBuilder.defaultGame.gameOver()
        Consumed
    }
}
