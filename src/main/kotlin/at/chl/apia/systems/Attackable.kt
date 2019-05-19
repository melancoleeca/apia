package at.chl.apia.systems

import at.chl.apia.attributes.types.combatStats
import at.chl.apia.commands.Attack
import at.chl.apia.commands.Destroy
import at.chl.apia.extensions.GameCommand
import at.chl.apia.extensions.isPlayer
import at.chl.apia.extensions.whenHasNoHealthLeft
import at.chl.apia.functions.logGameEvent
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object Attackable : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs(Attack::class) { (context, attacker, target) ->

        if (attacker.isPlayer || target.isPlayer) {

            val damage = Math.max(0, attacker.combatStats.attackValue - target.combatStats.defenseValue)
            val finalDamage = (Math.random() * damage).toInt() + 1
            target.combatStats.hp -= finalDamage

            logGameEvent("The $attacker hits the $target for $finalDamage!")

            target.whenHasNoHealthLeft {

                target.executeCommand(
                    Destroy(
                        context = context,
                        source = attacker,
                        target = target,
                        cause = "a blow to the head")
                )
                attacker.combatStats.bodyCount ++
            }

            Consumed
        } else Pass
    }
}
