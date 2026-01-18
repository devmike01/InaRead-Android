package dev.gbenga.inaread.ui.addreading

import android.net.Uri

@Deprecated("Marked for removal because of internal loop in viewmodel")

sealed interface AddReadingEvent {

    data class GetMeterImageResult(val imageUri: Uri): AddReadingEvent
    data class ToggleMeterImageButton(val enabled: Boolean): AddReadingEvent
    data object RemoveImage: AddReadingEvent
}