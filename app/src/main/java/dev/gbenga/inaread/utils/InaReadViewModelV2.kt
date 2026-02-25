package dev.gbenga.inaread.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gbenga.inaread.tokens.StringTokens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class InaReadViewModelV2 <S: InaReadUiState>(initialState: S) : ViewModel() {

    private val _snackbarEvent = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    private val _loadingEvent = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val loadingEvent = _loadingEvent.asSharedFlow()
    val snackBarEvent = _snackbarEvent.asSharedFlow()

    private val _state = MutableStateFlow<S>(initialState)
    val state = _state.asStateFlow()

    private val _navigator = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigator = _navigator.receiveAsFlow()

    protected fun navigate(navEvent: NavigationEvent){
        viewModelScope.launch {
            _navigator.send(navEvent)
        }
    }

    fun launchWithException(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        onError: suspend (String) -> Unit,
        block: suspend CoroutineScope.() -> Unit, ){

        viewModelScope.launch(context, start){
            try {
                block()
            }catch (exception: Exception){
                /**
                 * To force recomposition within a short period,
                 * the state is briefly changed and delayed for a min of 17ms before updating to the new state.
                 * Otherwise, no recomposition will happen.
                  */
                delay(20)
                onError(exception.message ?: StringTokens.UnknownErrorOccured)
            }
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

    protected fun showUiMessage(message: String){
        _snackbarEvent.tryEmit(message)
    }

    protected fun showLoading(){
        _loadingEvent.tryEmit(true)
    }

    protected fun hideLoading(){
        _loadingEvent.tryEmit(false)
    }

    @Deprecated("Call api direct in viewmodel")
    fun watchEvents(){}

}