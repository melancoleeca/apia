package at.chl.apia.attributes

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property

data class PlayerStats(val clearedMapsProperty: Property<Int>) : Attribute {
    var clearedMaps: Int by clearedMapsProperty.asDelegate()

    companion object {

        fun create(clearedMaps: Int = 0) =
            PlayerStats(
                clearedMapsProperty = createPropertyFrom(clearedMaps)
            )
    }
}
