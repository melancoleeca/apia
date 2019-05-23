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
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.logging.api.LoggerFactory

object AttackableMob : BaseBehavior<GameContext>() {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        logger.debug("update AttackableMob: ${entity.name}")

        val player = context.player

        player.position.sameLevelNeighborsShuffled()

        if(player.position.isInReach(entity.position, 1)){
            entity.executeCommand(Attack(context, entity as GameEntity<Combatant>, player.position))
        }

        return true
    }

}
