package dev.gbenga.inaread.ui.addreading

sealed interface AddReadingEvent {

    data class GetMeterImageResult(val imagePath: String): AddReadingEvent
}