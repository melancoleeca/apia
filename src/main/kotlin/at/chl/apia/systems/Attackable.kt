package at.chl.apia.systems

import at.chl.apia.attributes.types.Combatant
import at.chl.apia.attributes.types.combatStats
import at.chl.apia.commands.Attack
import at.chl.apia.commands.Destroy
import at.chl.apia.extensions.*
import at.chl.apia.functions.logGameEvent
import at.chl.apia.world.GameBuilder
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.logging.api.LoggerFactory

object Attackable : BaseFacet<GameContext>() {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs(Attack::class) { (context, attacker, targetPosition) ->

        logger.info("attack from ${attacker.position} to $targetPosition")

        val targetBlock = GameBuilder.defaultGame.world.fetchBlockAt(targetPosition)

        logger.info(""+targetBlock.isEmpty())
        val entities = targetBlock.get().entities
        logger.info(""+targetBlock.get().isOccupied)


        entities.forEach{ entity ->
            logger.info(entity.name)
            if (entity.type is Combatant){
                logger.info(entity.id.toString())
                val target : GameEntity<Combatant> = entity as GameEntity<Combatant>
                logger.info(target.combatStats.defenseValue.toString())
                val damage = Math.max(0, attacker.combatStats.attackValue - target.combatStats.defenseValue)
                val finalDamage = (Math.random() * damage).toInt() + 1
                target.combatStats.hp -= finalDamage

                logGameEvent("The $attacker hits the $targetPosition for $finalDamage!")
                target.whenHasNoHealthLeft {
                    //
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
            }
        }
        Pass
    }
}
