package at.chl.apia.systems

import at.chl.apia.attributes.types.Player
import at.chl.apia.commands.MoveDown
import at.chl.apia.commands.MoveTo
import at.chl.apia.commands.MoveUp
import at.chl.apia.extensions.GameEntity
import at.chl.apia.extensions.position
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent

object InputReceiver : BaseBehavior<GameContext>() {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun update(entity: GameEntity<out EntityType>, context: GameContext): Boolean {
        val (_, _, uiEvent, player) = context
        val currentPos = player.position
        if (uiEvent is KeyboardEvent) {

            logger.debug(uiEvent.code.toString())

            when (uiEvent.code) {

                KeyCode.KEY_W -> player.moveTo(currentPos.withRelativeY(-1), context)
                KeyCode.UP -> player.moveTo(currentPos.withRelativeY(-1), context)

                KeyCode.KEY_A -> player.moveTo(currentPos.withRelativeX(-1), context)
                KeyCode.LEFT -> player.moveTo(currentPos.withRelativeX(-1), context)

                KeyCode.KEY_S -> player.moveTo(currentPos.withRelativeY(1), context)
                KeyCode.DOWN -> player.moveTo(currentPos.withRelativeY(1), context)

                KeyCode.KEY_D -> player.moveTo(currentPos.withRelativeX(1), context)
                KeyCode.RIGHT -> player.moveTo(currentPos.withRelativeX(1), context)

                else -> {
                    logger.debug("UI Event ($uiEvent) does not have a corresponding command, it is ignored.")
                }
            }
        }
        return true
    }

    private fun GameEntity<Player>.moveTo(position: Position3D, context: GameContext) {
        executeCommand(MoveTo(context, this, position))
    }

}
