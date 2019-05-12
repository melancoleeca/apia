package at.chl.apia

import at.chl.apia.view.StartView
import org.hexworks.zircon.api.SwingApplications

fun main() {
    SwingApplications.startApplication(GameConfig.buildAppConfig()).dock(StartView())
}
