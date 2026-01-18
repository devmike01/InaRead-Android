package dev.gbenga.inaread.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class InaReadViewModel<S: InaReadUiState, E>(initialState: S) : ViewModel() {

    private val _events = Channel<E>(Channel.BUFFERED)
    private val _state = MutableStateFlow<S>(initialState)
    protected val events = _events.receiveAsFlow()
    val state = _state.asStateFlow()

    private val _navigator = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigator = _navigator.receiveAsFlow()

    protected fun navigate(navEvent: NavigationEvent){
        viewModelScope.launch {
            _navigator.send(navEvent)
        }
    }


    protected fun sendEvent(event: E){
        viewModelScope.launch {
            _events.send(event)
        }
    }

    protected fun setState(reducer: (S) -> S){
        _state.update (reducer)
    }

    protected fun setState(doBefore: (S) -> S, reducer: (S) -> S){
        _state.update { state ->
            reducer(doBefore(state))
        }
    }

    @Deprecated("Call api direct in viewmodel")
    fun watchEvents(){}

}