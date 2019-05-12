package at.chl.apia.functions

import at.chl.apia.events.GameLogEvent
import org.hexworks.zircon.internal.Zircon

fun logGameEvent(text: String) {
    Zircon.eventBus.publish(GameLogEvent(text))
}
