package at.chl.apia.view


import at.chl.apia.GameConfig
import at.chl.apia.attributes.types.combatStats
import at.chl.apia.blocks.GameBlock
import at.chl.apia.events.GameLogEvent
import at.chl.apia.world.Game
import at.chl.apia.world.GameBuilder
import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.events.api.Event
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.onComponentEvent
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.mvc.base.BaseView
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.Zircon


class PlayView(private val game: Game = GameBuilder.defaultGame()) : BaseView() {

    override val theme = GameConfig.THEME

    private val logger = LoggerFactory.getLogger("at.chl.apia.world.PlayView")

    override fun onDock() {

        screen.onKeyboardEvent(KeyboardEventType.KEY_PRESSED) { event, _ ->
            game.world.update(screen, event, game)
            Processed
        }

        val sidebar = Components.panel()
                .withSize(GameConfig.SIDEBAR_WIDTH, GameConfig.WINDOW_HEIGHT)
                .build()

        screen.addComponent(sidebar)

        val exitButton = Components.button()
            .withText("Exit")
            .wrapSides(false)
            .withBoxType(BoxType.SINGLE)
            .wrapWithBox()
            .build()

        sidebar.addComponent(exitButton)



        sidebar.addComponent(createCharacterInfo(beyond(exitButton)))

        // TODO: tutorial
        exitButton.onComponentEvent(ComponentEventType.ACTIVATED) {
            replaceWith(StartView())
            close()
            Processed
        }


        val logArea = Components.logArea()
                .withTitle("Log")
                .wrapWithBox()
                .withSize(GameConfig.WINDOW_WIDTH - GameConfig.SIDEBAR_WIDTH, GameConfig.LOG_AREA_HEIGHT)
                .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
                .build()

        screen.addComponent(logArea)

        Zircon.eventBus.subscribe<GameLogEvent> { (text) ->
            logArea.addParagraph(
                    paragraph = text,
                    withNewLine = false,
                    withTypingEffectSpeedInMs = 10)
        }





        val gameComponent = GameComponents.newGameComponentBuilder<Tile, GameBlock>()
                .withGameArea(game.world)
                .withVisibleSize(game.world.visibleSize())
                .withProjectionMode(ProjectionMode.TOP_DOWN)
                .withAlignmentWithin(screen, ComponentAlignment.TOP_RIGHT)
                .build()

        screen.addComponent(gameComponent)

    }

    private fun createCharacterInfo(withPosition: Position): Component {
        val characterInfo = Components.panel()
            .withTitle("Character")
            .withSize(GameConfig.SIDEBAR_WIDTH, GameConfig.CHARINFO_HEIGHT)
            .withPosition(withPosition)
            .wrapWithBox()
            .build()

        val nameLabel = Components.label().withText(game.player.name).build()
        characterInfo.addComponent(nameLabel)

        val healthLabel = Components.label()
                                .withText(game.player.combatStats.hp.toString() +  " / " + game.player.combatStats.maxHp)
                                .withPosition(beyondLeft(nameLabel)).build()
        characterInfo.addComponent(healthLabel)

        val bodyCountLabel = Components.label()
                                .withText(game.player.combatStats.bodyCount.toString())
                                .withPosition(beyondLeft(healthLabel))
                                .build()
        characterInfo.addComponent(bodyCountLabel)
        game.player.combatStats.bodyCountProperty.onChange{event ->
            logger.info(event.newValue.toString())
            bodyCountLabel.text = event.newValue.toString()
        }


        return characterInfo
    }
    private fun beyond(component: Component):Position{
        return component.absolutePosition.relativeToBottomOf(component)
    }

    private fun beyondLeft(component: Component):Position{
        return component.absolutePosition.relativeToLeftOf(component)
    }
}
