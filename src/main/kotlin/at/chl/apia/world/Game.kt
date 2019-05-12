package at.chl.apia.world

import at.chl.apia.attributes.types.Player
import at.chl.apia.extensions.GameEntity

class Game(val world: World,
           val player: GameEntity<Player>) {

    companion object {

        fun create(player: GameEntity<Player>,
                   world: World) = Game(
                world = world,
                player = player)
    }
}
