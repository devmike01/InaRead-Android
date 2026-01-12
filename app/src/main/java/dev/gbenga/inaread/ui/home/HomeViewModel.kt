package dev.gbenga.inaread.ui.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.domain.usescases.meter.MeterSummaryUseCase
import dev.gbenga.inaread.domain.usescases.meter.WeekDaysUseCase
import dev.gbenga.inaread.ui.customs.toUiState
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.UiState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weekDays: WeekDaysUseCase,
    private val meterUseCase: MeterSummaryUseCase,
) : InaReadViewModel<HomeUiState, HomeEvent>(
    HomeUiState()
) {


    fun loadWeekDays(){
        sendEvent(HomeEvent.LoadWeekDays)
    }

    fun loadMeterSummary(){
        sendEvent(HomeEvent.LoadMeterSummary)
    }

    override fun watchEvents(){
        viewModelScope.launch {
            events.collect { event ->
                when(event){
                    is HomeEvent.SelectDay ->{
                        setState { it.copy(weekDates = it.weekDates.mapIndexed { index, weekDay ->
                            weekDay.copy(selected = index == event.position)
                        }) }
                    }
                    is HomeEvent.AddReading -> {

                    }
                    is HomeEvent.GotoHome -> {

                    }
                    is HomeEvent.GotoSetting -> {

                    }
                    is HomeEvent.LoadWeekDays ->  setState {
                        it.copy(weekDates = weekDays.invoke())
                    }
                    is HomeEvent.LoadMeterSummary ->   {
                        meterUseCase.invoke()
                            .map { usage ->  usage.toUiState() }
                            .onStart {
                                setState { it.copy(meterUsageSummary = UiState.Loading) }
                            }
                            .collect { usageSummary ->  setState { it.copy( meterUsageSummary = usageSummary) } }
                    }
                }
            }
        }
    }

    fun selectDay(index: Int){
        sendEvent(HomeEvent.SelectDay(index))
    }

}