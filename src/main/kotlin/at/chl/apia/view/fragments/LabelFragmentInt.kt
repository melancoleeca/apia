package at.chl.apia.view.fragments

import at.chl.apia.GameConfig
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment

class LabelFragmentInt(var label: String, var property: Property<Int>) : Fragment {
    override val root : Component = createRoot()



    private fun createRoot(): Component {
        val width = GameConfig.LABEL_WITH
        return Components.hbox().withSize(width, 1).withSpacing(0).build().apply {
            addComponent(Components.label().withText(label).withSize(width/2, 1))
            addComponent(Components.label().withText(property.value.toString()).withSize(width/2, 1).build().apply {
                property.onChange{event ->
                                text = event.newValue.toString()
        }
            })
        }
    }
}
