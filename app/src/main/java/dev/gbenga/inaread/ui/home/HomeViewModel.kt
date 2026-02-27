package dev.gbenga.inaread.ui.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Timeline
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.R
import dev.gbenga.inaread.data.datastore.Messenger
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.LastPowerUsage
import dev.gbenga.inaread.data.model.MeterMonthlyStat
import dev.gbenga.inaread.data.model.MonthlyMeterUsage
import dev.gbenga.inaread.data.repository.AuthRepositoryImpl.Companion.TAG
import dev.gbenga.inaread.domain.providers.CalendarProvider
import dev.gbenga.inaread.domain.usecase.GetProfileUseCase
import dev.gbenga.inaread.domain.usecase.WeekDaysUseCase
import dev.gbenga.inaread.domain.usecase.MeterSummaryUseCase
import dev.gbenga.inaread.ui.customs.toUiState
import dev.gbenga.inaread.ui.theme.DeepOrange
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.Scada
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.date.InaDateFormatter
import dev.gbenga.inaread.utils.ext.naira
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weekDays: WeekDaysUseCase,
    private val meterUseCase: MeterSummaryUseCase,
    private val calendarProvider: CalendarProvider,
    private val savedStateHandle: SavedStateHandle,
    private val inaDateFormatter: InaDateFormatter,
    private val getProfileUseCase: GetProfileUseCase,
    private val messenger: Messenger
) : InaReadViewModelV2<HomeUiState>(
    HomeUiState()
) {

    companion object{
        const val CURRENT_DASHBOARD_ROUTE = "HomeViewModel.CURRENT_DASHBOARD_ROUTE"
        const val SELECTED_DATE = "HomeViewModel.SELECTED_DATE"
        const val SELECTED_CALENDAR_POS = "HomeViewModel.SELECTED_CALENDAR_POS"
    }

    init {
        populateDashboard()
        setProfileInitial()
    }


    private fun setProfileInitial(){
        viewModelScope.launch {
            when(val profile = getProfileUseCase()){
                is RepoResult.Success -> {
                    setState { it.copy(nameInitial = profile.data
                        .username[0].uppercase()) }
                }
                is RepoResult.Error -> {
                    setState { it.copy(nameInitial = "n\\a") }
                }
            }
        }
    }


    fun receiveMessage(){
        viewModelScope.launch {
            //delay(1000)
            messenger.receiveMessage().collect { message ->
                message?.let {
                    showUiMessage(message = message)
                }
            }
        }
    }
    fun populateDashboard(){
        val selectedDate = savedStateHandle.get<String>(SELECTED_DATE)
        val selectedPos = savedStateHandle.get<Int>(SELECTED_CALENDAR_POS)

        getTopbarContent()

        getSummary(calendarProvider
            .getTodayYMD())

        val scrollToPos = calendarProvider.getIndexOf(calendarProvider.getCurrentDayOfMonth())
        selectNewDay(selectedDate
            ?: calendarProvider.getTodayYMD(), selectedPos ?: scrollToPos)
    }

    fun gotoAllUnitUsage(){
        navigate(NavigationEvent.Navigate(InaScreen.AllUnitUsage))
    }


    private fun Long.toNaira(): String{
        return BigDecimal(this)
            .divide(100.toBigDecimal()).toPlainString()
    }

    fun selectDay(dayOfMonth: Int, index: Int){
        if (dayOfMonth != savedStateHandle[SELECTED_DATE]){
            savedStateHandle[SELECTED_DATE] = dayOfMonth
            savedStateHandle[SELECTED_CALENDAR_POS] = index
        }

//        sendEvent(HomeEvent.LoadMeterSummary(dayOfMonth))
//        sendEvent(HomeEvent.SelectDay(dayOfMonth, index))
    }

    fun selectNewDay(ymdDateStr: String, selectedPos: Int){
        if (ymdDateStr != savedStateHandle[SELECTED_DATE]){
            savedStateHandle[SELECTED_DATE] = ymdDateStr
            savedStateHandle[SELECTED_CALENDAR_POS] = selectedPos
        }


        // c867773d0beb4bc1877209acd959da57

        getSummary(ymdDateStr)

        setState { it.copy(daysOfMonth = it.daysOfMonth.map {  weekDay ->
            Scada.info("dayOfMonth: ${weekDay.dayOfMonth}")
            weekDay.copy(selected = weekDay.ymdDateStr == ymdDateStr,
                dayIsAvailable = it.daysOfMonth.any{ dOM -> dOM.ymdDateStr == ymdDateStr},
            )
        }, selectedCalendarPos = selectedPos) }
    }

    fun getTopbarContent(){
        setState { it.copy(greeting = calendarProvider.greetBasedOnTime(),
            todaysDate = calendarProvider.getToday(),
            daysOfMonth = weekDays()
            ) }
    }

    fun getSummary(fromDayOfMonth: String){
        viewModelScope.launch {
            setState{it.copy( meterUsageSummary = UiState.Loading)}
            when(val result = meterUseCase(fromDayOfMonth)){
                is RepoResult.Success -> {

                    val summary = result.data.firstOrNull()
                    setState { it.copy(

                        meterUsageSummary =  UiState.Success(Pair(
                            summary?.let { summary ->
                                UiData.Content(MeterMonthlyStat(
                                    chartData = summary.monthlyUsage,

                                    lifeTimeReading = VectorInaTextIcon(
                                        icon = Icons.Default.ElectricMeter,
                                        value = summary.totalMonthPowerUsage
                                            .setScale(2, RoundingMode.HALF_EVEN)
                                            .toPlainString(),
                                        label = "kWh",
                                        color = 0xFFFF5722,
                                    ),
                                    monthlyStat = VectorInaTextIcon(
                                        icon = Icons.Default.Timeline,
                                        value = summary.periodInDays,
                                        label = "Period(days)",
                                        color = 0xFFFF5722,
                                    ),
                                    costStat = VectorInaTextIcon(
                                        icon = Icons.Default.Money,
                                        value = summary.costPerKwh.naira(),
                                        label = "Cost/KWh",
                                        color = 0xFFFF5722,
                                    ),
                                )) } ?: UiData.EmptyContent,
                            summary?.let {
                                UiData.Content(
                                    listOf(
                                        ResIdInaTextIcon(
                                            icon = R.drawable.outline_money_bag_24,
                                            value = summary.totalSpent.naira(),
                                            label = "Total spend",
                                            color = 0xFFFF5722,
                                        ),
                                        ResIdInaTextIcon(
                                            icon = R.drawable.outline_electric_bolt_24,
                                            value = summary.meterType,
                                            label = "Subscription type",
                                            color = 0xFFFF5722,
                                        ),
                                        ResIdInaTextIcon( //MMM d
                                            icon = R.drawable.outline_calendar_today_24,
                                            value = inaDateFormatter.ddMMM(summary.fromDate),
                                            label = "From",
                                            color = 0xFFFF5722,
                                        )
                                    )
                                )
                            }?: UiData.EmptyContent,
                        ))
                    ) }
                }
                is RepoResult.Error -> {
                    setState { it.copy(
                        meterUsageSummary = UiState.Error(result.message)
                    ) }
                }
            }
        }
    }

}