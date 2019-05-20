package at.chl.apia.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType(
        name = "player"), Combatant, PlayerType

object Wall : BaseEntityType(
        name = "wall")

object Fungus : BaseEntityType(
        name = "fungus"), Combatant

object StairsDown : BaseEntityType(
        name = "stairs down")

object StairsUp : BaseEntityType(
        name = "stairs up")
