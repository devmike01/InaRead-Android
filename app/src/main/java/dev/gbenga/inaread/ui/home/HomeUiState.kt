package dev.gbenga.inaread.ui.home

import dev.gbenga.inaread.data.model.MeterMonthlyStat
import dev.gbenga.inaread.ui.dashboard.DashboardScreen
import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiState

data class HomeUiState(
    val weekDates : CalendarTileWeekDays = emptyList(),
    val meterUsageSummary: UiState<Pair<MeterMonthlyStat, List<ResIdInaTextIcon>>> = UiState.Loading,
    val greeting: String = "",
    val todaysDate: String = "",
    val selectedPage: DashboardScreen = DashboardScreen.HomeScreen(),
): InaReadUiState

/*
lifeTimeReading: HomeStat,
                  monthlyStat: HomeStat,
                  costStat: HomeStat,
                  chartData: List<Float>
 */