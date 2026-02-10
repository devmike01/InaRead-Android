package dev.gbenga.inaread.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.R
import dev.gbenga.inaread.data.model.LastPowerUsage
import dev.gbenga.inaread.data.model.MeterMonthlyStat
import dev.gbenga.inaread.data.model.MonthlyMeterUsage
import dev.gbenga.inaread.domain.providers.CalendarProvider
import dev.gbenga.inaread.domain.usecase.WeekDaysUseCase
import dev.gbenga.inaread.domain.usecase.MeterSummaryUseCase
import dev.gbenga.inaread.ui.customs.toUiState
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.Scada
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.flow.catch
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
) : InaReadViewModelV2<HomeUiState>(
    HomeUiState()
) {

    companion object{
        const val CURRENT_DASHBOARD_ROUTE = "HomeViewModel.CURRENT_DASHBOARD_ROUTE"
        const val SELECTED_DATE = "HomeViewModel.SELECTED_DATE"
        const val SELECTED_CALENDAR_POS = "HomeViewModel.SELECTED_CALENDAR_POS"
    }



    fun populateDashboard(){
        val selectedDate = savedStateHandle.get<Int>(SELECTED_DATE)
        val selectedPos = savedStateHandle.get<Int>(SELECTED_CALENDAR_POS)

        getTopbarContent()
        getSummary(calendarProvider
            .getCurrentDayOfMonth())

        val scrollToPos = calendarProvider.getIndexOf(calendarProvider.getCurrentDayOfMonth())
        selectNewDay(selectedDate
            ?: calendarProvider.getCurrentDayOfMonth(), selectedPos ?: scrollToPos)
    }

    fun gotoAllUnitUsage(){
        navigate(NavigationEvent.Navigate(InaScreen.AllUnitUsage))
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

    fun selectDay(dayOfMonth: Int, index: Int){
        if (dayOfMonth != savedStateHandle[SELECTED_DATE]){
            savedStateHandle[SELECTED_DATE] = dayOfMonth
            savedStateHandle[SELECTED_CALENDAR_POS] = index
        }

//        sendEvent(HomeEvent.LoadMeterSummary(dayOfMonth))
//        sendEvent(HomeEvent.SelectDay(dayOfMonth, index))
    }

    fun selectNewDay(dayOfMonth: Int, selectedPos: Int){
        if (dayOfMonth != savedStateHandle[SELECTED_DATE]){
            savedStateHandle[SELECTED_DATE] = dayOfMonth
            savedStateHandle[SELECTED_CALENDAR_POS] = selectedPos
        }

        getSummary(dayOfMonth)

        setState { it.copy(daysOfMonth = it.daysOfMonth.map {  weekDay ->
            Scada.info("dayOfMonth: ${weekDay.dayOfMonth}")
            weekDay.copy(selected = weekDay.dayOfMonth == dayOfMonth,
                dayIsAvailable = it.daysOfMonth.any{ dOM -> dOM.dayOfMonth == dayOfMonth},
            )
        }, selectedCalendarPos = selectedPos) }
    }

    fun getTopbarContent(){
        setState { it.copy(greeting = calendarProvider.greetBasedOnTime(),
            todaysDate = calendarProvider.getToday(),
            daysOfMonth = weekDays()
            ) }
    }

    fun getSummary(fromDayOfMonth: Int){
        viewModelScope.launch {

            meterUseCase()
                .map { usage ->  usage.map {
                    val usagesByDay = it.usages.associateBy { usage -> usage.fromDayOfMonth }
                    Pair(it.monthlyMeterUsage.toMeterMonthlyStat(),
                        usagesByDay[fromDayOfMonth]?.toResIdIconItems() ?: emptyList())
                } }
                .onStart {
                    setState { it.copy(meterUsageSummary = UiState.Loading) }
                }
                .catch {
                    Scada.error("---------\nMonth_dayOfMonth: $it")
                }

                .collect { usageSummary ->
                    Scada.info("---------\nMonth_dayOfMonth: ${usageSummary.getOrNull()?.second}")

                    setState { it.copy(
                        meterUsageSummary = usageSummary.toUiState(),
                        selectedDateValue = calendarProvider.getFullDateFrom(fromDayOfMonth)
                    ) }
                }
        }
    }

}