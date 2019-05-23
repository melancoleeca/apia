package at.chl.apia.attributes

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property

data class MobStats(val levelProperty: Property<Int>) : Attribute {
    val level: Int by levelProperty.asDelegate()
    companion object {

        fun create(level: Int = 0) =
                MobStats(
                        levelProperty = createPropertyFrom(level)
                )
    }
}
