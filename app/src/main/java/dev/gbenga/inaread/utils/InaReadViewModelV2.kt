package dev.gbenga.inaread.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class InaReadViewModelV2 <S: InaReadUiState>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow<S>(initialState)
    val state = _state.asStateFlow()


    private val _uIState = MutableStateFlow<UiStateWithIdle<S>>(UiStateWithIdle.Idle)
    val uiState = _uIState.asStateFlow()


    private val _navigator = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigator = _navigator.receiveAsFlow()

    protected fun navigate(navEvent: NavigationEvent){
        viewModelScope.launch {
            _navigator.send(navEvent)
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