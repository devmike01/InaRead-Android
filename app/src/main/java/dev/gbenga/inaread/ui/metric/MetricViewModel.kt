package dev.gbenga.inaread.ui.metric

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.model.toAppliance
import dev.gbenga.inaread.data.model.toMonthValue
import dev.gbenga.inaread.domain.metrics.GetAppliancesUseCase
import dev.gbenga.inaread.domain.metrics.GetMonthlyChartUseCase
import dev.gbenga.inaread.utils.InaReadViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetricViewModel @Inject constructor(private val getAppliancesUseCase: GetAppliancesUseCase,
                      private val getMonthlyChartUseCase: GetMonthlyChartUseCase) : InaReadViewModel<MetricUiState, MetricEvent>(MetricUiState()) {


    fun populate(){

        viewModelScope.launch {
            val appliances = getAppliancesUseCase().map { a -> a.toAppliance() }
            val monthlyChart = getMonthlyChartUseCase()
                .map { it.toMonthValue() }
            setState { it.copy(
                monthChartValues = monthlyChart,
                appliances = appliances
            ) }
        }
    }
}