package network.ramp.instantsdk.events.model

sealed class InternalEvent {
    object CLOSE : InternalEvent()
}