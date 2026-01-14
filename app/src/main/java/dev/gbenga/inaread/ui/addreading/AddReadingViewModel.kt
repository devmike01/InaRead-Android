package dev.gbenga.inaread.ui.addreading

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.utils.InaReadViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReadingViewModel @Inject constructor() : InaReadViewModel<AddReadingState, AddReadingEvent>(AddReadingState()) {

    override fun watchEvents() {
        viewModelScope.launch {
            events.collect { events ->
                when(events){
                    is AddReadingEvent.GetMeterImageResult -> {
                        setState { it.copy(
                            meterImagePath = events.imagePath)
                        }
                    }
                }
            }
        }
    }

    fun addImage(path: String?){
        path?.let {
            sendEvent(AddReadingEvent.GetMeterImageResult(path))
        }
    }
}