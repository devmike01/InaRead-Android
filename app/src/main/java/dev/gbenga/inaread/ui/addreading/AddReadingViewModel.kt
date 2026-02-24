package dev.gbenga.inaread.ui.addreading

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.domain.providers.ImagePickerProvider
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.Scada
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReadingViewModel @Inject constructor(
    private val imagePickerProvider: ImagePickerProvider) : InaReadViewModel<AddReadingState, AddReadingEvent>(AddReadingState()) {


    fun addImage(imageUri: Uri?){
        viewModelScope.launch {
            imageUri?.let { imageUri->

                val selectedImagePath = imagePickerProvider
                    .getAbsolutePathFor(imageUri)
                Scada.info("AddReadingEvent: $selectedImagePath")
                setState { it.copy(
                    meterImagePath = selectedImagePath)
                }
            }
            toggleImage(imageUri != null)
        }

    }

    fun toggleRecordMeterReading(){
        setState { state -> state.copy(
            record = MeterReadingRecord.MANUAL
                .takeIf { state.record ==  MeterReadingRecord.OCR}
                ?: MeterReadingRecord.OCR
        ) }
    }

    private fun toggleImage(enabled: Boolean){
        setState { it.copy(
            enableReadImage = enabled)
        }
    }

    fun removeImage(){
        setState { it.copy(meterImagePath = null) }
        toggleImage(false)
    }

}