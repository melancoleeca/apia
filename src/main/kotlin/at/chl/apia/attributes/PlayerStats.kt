package at.chl.apia.attributes

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property

data class PlayerStats(val levelProperty: Property<Int>, val nameProperty: Property<String>) : Attribute {
    var level: Int by levelProperty.asDelegate()
    var name: String by nameProperty.asDelegate()

    companion object {

        fun create(level: Int = 1, name: String = "Mia") =
            PlayerStats(
                levelProperty = createPropertyFrom(level),
                nameProperty = createPropertyFrom(name)
            )
    }
}
