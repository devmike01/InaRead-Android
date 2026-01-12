package dev.gbenga.inaread.ui.home

import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiState

data class HomeUiState(
    val weekDates : CalendarTileWeekDays = emptyList(),
    val meterUsageSummary: UiState<MeterUsageSummary> = UiState.Loading,
    val stats : StatsChart = StatsChart()
): InaReadUiState

/*
lifeTimeReading: HomeStat,
                  monthlyStat: HomeStat,
                  costStat: HomeStat,
                  chartData: List<Float>
 */