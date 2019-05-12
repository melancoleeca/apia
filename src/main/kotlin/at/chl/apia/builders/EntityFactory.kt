package at.chl.apia.builders

import at.chl.apia.attributes.*
import at.chl.apia.attributes.flags.BlockOccupier
import at.chl.apia.attributes.types.*
import at.chl.apia.commands.Attack
import at.chl.apia.commands.Dig
import at.chl.apia.systems.*
import at.chl.apia.world.GameContext
import org.hexworks.amethyst.api.Entities
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType

fun <T : EntityType> newGameEntityOfType(type: T, init: EntityBuilder<T, GameContext>.() -> Unit) =
        Entities.newEntityOfType(type, init)

object EntityFactory {

    fun newWall() = newGameEntityOfType(Wall) {
        attributes(
                EntityPosition(),
                BlockOccupier,
                EntityTile(GameTileRepository.WALL)
        )
        facets(Diggable)
    }

    fun newStairsDown() = newGameEntityOfType(StairsDown) {
        attributes(EntityTile(GameTileRepository.STAIRS_DOWN),
                EntityPosition())
    }

    fun newStairsUp() = newGameEntityOfType(StairsUp) {
        attributes(EntityTile(GameTileRepository.STAIRS_UP),
                EntityPosition())
    }

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(
                EntityPosition(),
                CombatStats.create(
                        maxHp = 100,
                        attackValue = 10,
                        defenseValue = 5),
                EntityTile(GameTileRepository.PLAYER),
                EntityActions(Dig::class, Attack::class)
        )
        behaviors(InputReceiver)
        facets(Movable, CameraMover, StairClimber, StairDescender)
    }

    fun newFungus(fungusSpread: FungusSpread = FungusSpread()) = newGameEntityOfType(Fungus) {
        attributes(BlockOccupier,
                EntityPosition(),
                CombatStats.create(
                        maxHp = 10,
                        attackValue = 0,
                        defenseValue = 0),
                EntityTile(GameTileRepository.FUNGUS),
                fungusSpread)
        facets(Attackable, Destructible)
        behaviors(FungusGrowth)
    }
}

