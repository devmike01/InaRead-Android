package dev.gbenga.inaread.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class InaReadViewModel<E : InaReadEvent> : ViewModel() {

    private val _events = Channel<E>(Channel.BUFFERED)

    val events = _events.receiveAsFlow()

    fun sendEvent(event: E){
        viewModelScope.launch {
            _events.send(event)
        }
    }
}