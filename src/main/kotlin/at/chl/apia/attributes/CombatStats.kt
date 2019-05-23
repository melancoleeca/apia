package at.chl.apia.attributes

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property

data class CombatStats(val maxHpProperty: Property<Int>,
                       val hpProperty: Property<Int> = createPropertyFrom(maxHpProperty.value),
                       val attackValueProperty: Property<Int>,
                       val defenseValueProperty: Property<Int>,
                       val bodyCountProperty: Property<Int>) : Attribute {
    val maxHp: Int by maxHpProperty.asDelegate()
    var hp: Int by hpProperty.asDelegate()
    val attackValue: Int by attackValueProperty.asDelegate()
    val defenseValue: Int by defenseValueProperty.asDelegate()
    var bodyCount: Int by bodyCountProperty.asDelegate()

    companion object {

        fun create(maxHp: Int, hp: Int = maxHp, attackValue: Int, defenseValue: Int, bodyCount: Int = 0, level: Int = 1) =
                CombatStats(
                        maxHpProperty = createPropertyFrom(calcAmount(maxHp,level)),
                        hpProperty = createPropertyFrom(calcAmount(maxHp,level)),
                        attackValueProperty = createPropertyFrom(calcAmount(attackValue,level)),
                        defenseValueProperty = createPropertyFrom(calcAmount(defenseValue,level)),
                        bodyCountProperty = createPropertyFrom(bodyCount))

        fun calcAmount(amount:Int, level:Int): Int{
            return amount * ( 1  + level / 3)
        }

    }
}
