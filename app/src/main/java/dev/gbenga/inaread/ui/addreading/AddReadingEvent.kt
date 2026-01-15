package dev.gbenga.inaread.ui.addreading

import android.net.Uri

sealed interface AddReadingEvent {

    data class GetMeterImageResult(val imageUri: Uri): AddReadingEvent
}