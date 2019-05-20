package at.chl.apia.view.fragments

import at.chl.apia.GameConfig
import at.chl.apia.attributes.types.combatStats
import at.chl.apia.attributes.types.playerStats
import at.chl.apia.world.Game
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment

class CharacterInfoFragment(var game: Game) : Fragment {
    override val root = createRoot()

    private fun createRoot(): Component {

        return Components.vbox().withSpacing(1)
            .withSize(GameConfig.CHARINFO_WIDTH,GameConfig.CHARINFO_HEIGHT-4)
            .build().apply {
                val list = this
                addComponent(Components.hbox()      // 2
                    .withSpacing(1)
                    .withSize(width, 1)
                    .build().apply {
//                        addComponent(Components.label().withText("").withSize(1, 1))
//                        addComponent(Components.header().withText("Name").withSize(NAME_COLUMN_WIDTH, 1))
//                        addComponent(Components.header().withText("Actions").withSize(ACTIONS_COLUMN_WIDTH, 1))
                    })
                addFragment(LabelFragmentString("Name",game.player.playerStats.nameProperty))
                addFragment(LabelFragmentInt("Health",game.player.combatStats.hpProperty))
                addFragment(LabelFragmentInt("Level",game.player.playerStats.levelProperty))
            }
    }

}
