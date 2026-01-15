package dev.gbenga.inaread.ui.metric

import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.data.model.MonthValue
import dev.gbenga.inaread.utils.InaReadUiState

data class MetricUiState(val monthChartValues: List<MonthValue> = emptyList(),
    val appliances: List<Appliance> = emptyList()) : InaReadUiState