package network.ramp.instantsdk.facade

import network.ramp.instantsdk.events.model.Event

interface EventListener {
    fun on(event: Event)
}