package at.chl.apia.view

import at.chl.apia.world.GameBuilder
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.extensions.onComponentEvent
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.mvc.base.BaseView
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.Processed

class StartView : BaseView() {

    override val theme = ColorThemes.arc()

    override fun onDock() {
        val msg = "~~ APIA ~~"
        val header = Components.textBox()
                .withContentWidth(msg.length)
                .addHeader(msg)
                .addNewLine()
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build()
        val startButton = Components.button()
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_CENTER)
                .withText("Start!")
                .wrapSides(false)
                .withBoxType(BoxType.SINGLE)
                .wrapWithShadow()
                .wrapWithBox()
                .build()

        // TODO: tutorial
        startButton.onComponentEvent(ComponentEventType.ACTIVATED) {

            GameBuilder.defaultGame.startMap()
            close()
            Processed
        }
        screen.addComponent(header)
        screen.addComponent(startButton)
    }
}
