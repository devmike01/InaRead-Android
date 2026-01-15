package dev.gbenga.inaread.ui.addreading

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.domain.providers.ImagePickerProvider
import dev.gbenga.inaread.utils.InaReadViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReadingViewModel @Inject constructor(private val imagePickerProvider: ImagePickerProvider) : InaReadViewModel<AddReadingState, AddReadingEvent>(AddReadingState()) {

    override fun watchEvents() {
        viewModelScope.launch {
            events.collect { events ->
                when(events){
                    is AddReadingEvent.GetMeterImageResult -> {
                        val selectedImagePath = imagePickerProvider.getAbsolutePathFor(events.imageUri)
                        setState { it.copy(
                            meterImagePath = selectedImagePath)
                        }
                    }
                }
            }
        }
    }

    fun addImage(uri: Uri?){
        uri?.let {
            sendEvent(AddReadingEvent.GetMeterImageResult(uri))
        }
    }
}