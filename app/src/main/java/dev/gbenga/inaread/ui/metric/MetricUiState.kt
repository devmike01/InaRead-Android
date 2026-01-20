package dev.gbenga.inaread.ui.metric

import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.data.model.MonthValue
import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiStateWithIdle

data class MetricUiState(
    val monthChartValues: UiStateWithIdle<List<MonthValue>> = UiStateWithIdle.Idle,
    val appliances: UiStateWithIdle<List<Appliance>> = UiStateWithIdle.Idle) : InaReadUiState