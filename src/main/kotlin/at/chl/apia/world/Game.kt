package at.chl.apia.world

import at.chl.apia.attributes.types.Player
import at.chl.apia.attributes.types.playerStats
import at.chl.apia.extensions.GameEntity
import at.chl.apia.view.Director

class Game(val player: GameEntity<Player>) {

    lateinit var world : World

    companion object {

        fun create(player: GameEntity<Player>) = Game(
                player = player)
    }

    fun exitMap(){
        player.playerStats.level++
        Director.default.camp()
    }

    fun startMap() {

        prepareWorld()
        Director.default.play()
    }

    private fun prepareWorld() = also {
        world = GameBuilder.buildWorld()
        world.addAtStartingPosition(player)
        world.scrollUpBy(world.actualSize().zLength)
        MobPopulator().populateWorld(world,player.playerStats.level+1)
    }

    fun gameOver() {
        Director.default.gameOver()
    }
}
