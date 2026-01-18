package dev.gbenga.inaread.ui.home

import dev.gbenga.inaread.data.model.MeterMonthlyStat
import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.nav.DashboardScreen

data class HomeUiState(
    val daysOfMonth : CalendarTileWeekDays = emptyList(),
    val meterUsageSummary: UiState<Pair<MeterMonthlyStat, List<ResIdInaTextIcon>>> = UiState.Loading,
    val selectedDateValue: String ="",
    val greeting: String = "",
    val todaysDate: String = "",
    val selectedCalendarPos: Int = 0,
    val selectedPage: DashboardScreen = DashboardScreen.HomeScreen(),
): InaReadUiState

/*
lifeTimeReading: HomeStat,
                  monthlyStat: HomeStat,
                  costStat: HomeStat,
                  chartData: List<Float>
 */