package dev.gbenga.inaread.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.R
import dev.gbenga.inaread.data.model.LastPowerUsage
import dev.gbenga.inaread.data.model.MeterMonthlyStat
import dev.gbenga.inaread.data.model.MonthlyMeterUsage
import dev.gbenga.inaread.domain.date.CalendarProvider
import dev.gbenga.inaread.domain.home.WeekDaysUseCase
import dev.gbenga.inaread.domain.meter.MeterSummaryUseCase
import dev.gbenga.inaread.ui.customs.toUiState
import dev.gbenga.inaread.ui.theme.Indigo100
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.UiState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weekDays: WeekDaysUseCase,
    private val meterUseCase: MeterSummaryUseCase,
    private val calendarProvider: CalendarProvider,
    private val savedStateHandle: SavedStateHandle,
) : InaReadViewModel<HomeUiState, HomeEvent>(
    HomeUiState()
) {

    companion object{
        const val CURRENT_DASHBOARD_ROUTE = "HomeViewModel.CURRENT_DASHBOARD_ROUTE"
        const val SELECTED_DATE = "HomeViewModel.SELECTED_DATE"
    }



    fun populateDashboard(){
        val selectedDate = savedStateHandle.get<Int>(SELECTED_DATE)

        selectedDate?.let {
            sendEvent(HomeEvent.SelectDay(it))
        }

        sendEvent(HomeEvent.LoadTodaysDate)
        sendEvent(HomeEvent.LoadGreeting)
        sendEvent(HomeEvent.LoadMeterSummary)
        sendEvent(HomeEvent.LoadWeekDays)
    }

    override fun watchEvents(){
        viewModelScope.launch {
            events.collect { event ->
                when(event){
                    is HomeEvent.SelectDay ->{
                        if (event.position != savedStateHandle[SELECTED_DATE]){
                            savedStateHandle[SELECTED_DATE] = event.position
                        }
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
                            .map { usage ->  usage.map {
                                Pair(it.monthlyMeterUsage.toMeterMonthlyStat(),
                                    it.lastUsage.toResIdIconItems())
                            } }
                            .onStart {
                                setState { it.copy(meterUsageSummary = UiState.Loading) }
                            }
                            .collect { usageSummary ->  setState { it.copy(
                                meterUsageSummary = usageSummary.toUiState() ) } }
                    }

                    HomeEvent.LoadGreeting -> {
                        setState { it.copy(greeting = calendarProvider.greetBasedOnTime()) }
                    }
                    HomeEvent.LoadTodaysDate -> {
                        setState { it.copy(todaysDate = calendarProvider.getToday()) }
                    }

                }
            }
        }
    }

    private fun MonthlyMeterUsage.toMeterMonthlyStat(): MeterMonthlyStat {
        return MeterMonthlyStat(
            lifeTimeReading = ResIdInaTextIcon(
                value = kilowatts.toString(),
                label = "Usage(kW)", // kilowatts per period
                color = 0xFF42A5F5,
            ),
            monthlyStat = ResIdInaTextIcon(
                value = "${"\u20A6"}225",
                label = "Naira/kW", // kilowatts per period
                color = 0xFF42A5F5,
            ),
            costStat = ResIdInaTextIcon(
                value = minorCost.toNaira(),
                label = "Spent(\u20A6)", // kilowatts per period
                color = 0xFF42A5F5,
            ),
            chartData = usages
        )
    }

    private fun Long.toNaira(): String{
        return BigDecimal(this)
            .divide(100.toBigDecimal()).toPlainString()
    }

    private fun LastPowerUsage.toResIdIconItems(): List<ResIdInaTextIcon>{
        val powerCostInNaira = minorCost.toNaira()

        return listOf(
            ResIdInaTextIcon(
                value = kilowatts.toString(),
                label = "Usage(kW)", // kilowatts per period
                color = 0xFFFFB74D,
                icon = R.drawable.outline_electric_bolt_24
            ),
            ResIdInaTextIcon(
                value = periodInDays.toString(),
                label = "Period(days)", // kilowatts per month
                color = 0xffC5CAE9,
                icon = R.drawable.outline_calendar_today_24
            ),
            ResIdInaTextIcon(
                value = powerCostInNaira,
                label = "Cost(Naira)", // kilowatts per month
                color = 0xFF4CAF50,
                icon = R.drawable.outline_money_bag_24
            )
        )
    }



    fun selectDay(index: Int){
        sendEvent(HomeEvent.SelectDay(index))
    }

}