package dev.gbenga.inaread.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class CalendarTileState  {

    private val _state = mutableStateOf(CalendarTileData(
        "",
        "",
        2, 1,
        false,
        0L,false,
        ))
    var value by _state
        private set
    
    fun select(selection: CalendarTileData){
        value = selection
    }
}

@Deprecated("Use viewmodel state")
@Composable
fun rememberCalendarTileState(): CalendarTileState = rememberSaveable(saver = CalendarTileStateSaver) {
    CalendarTileState()
}


inline fun <reified T> Map<String, Any?>.take(key: String): T{
    val value = this[key]
    return if (value is T){
        value
    }else throw IllegalArgumentException("Expected key $key of type ${T::class.simpleName}")
}

val CalendarTileStateSaver = mapSaver(
    save = { it.value.asMap() },
    restore = {
        CalendarTileState().apply {
            select(
                CalendarTileData(
                    it.take(CalendarParamNames.MONTH),
                    it.take(CalendarParamNames.YMD_DATE),
                    it.take(CalendarParamNames.MONTH_VALUE),
                    it.take(CalendarParamNames.DAY),
                    it.take(CalendarParamNames.DAY_IN_MILLIS),
                    it.take(CalendarParamNames.ID),
                    it.take(CalendarParamNames.IS_AVAILABLE))
            )
        }
    }
)