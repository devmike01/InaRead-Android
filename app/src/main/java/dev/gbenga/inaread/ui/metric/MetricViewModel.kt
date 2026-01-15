package dev.gbenga.inaread.ui.metric

import androidx.lifecycle.viewModelScope
import dev.gbenga.inaread.data.model.toAppliance
import dev.gbenga.inaread.data.model.toMonthValue
import dev.gbenga.inaread.domain.metrics.GetAppliancesUseCase
import dev.gbenga.inaread.domain.metrics.GetMonthlyChartUseCase
import dev.gbenga.inaread.utils.InaReadViewModel
import kotlinx.coroutines.launch

class MetricViewModel(private val getAppliancesUseCase: GetAppliancesUseCase,
                      private val getMonthlyChartUseCase: GetMonthlyChartUseCase) : InaReadViewModel<MetricUiState, MetricEvent>(MetricUiState()) {

    override fun watchEvents() {
        viewModelScope.launch {
            events.collect { uiEvent ->
                when(uiEvent){
                    is MetricEvent.LoadAppliances -> {
                        val monthlyChart = getMonthlyChartUseCase.invoke()
                            .map { it.toMonthValue() }
                        setState { it.copy(
                            monthChartValues = monthlyChart
                        ) }
                    }
                    is MetricEvent.PopulateScreen -> {
                        val appliances = getAppliancesUseCase.invoke().map { a -> a.toAppliance() }
                        setState { it.copy(appliances = appliances) }
                    }
                }
            }
        }
    }

    fun populate(){
        sendEvent(MetricEvent.PopulateScreen)
        sendEvent(MetricEvent.LoadAppliances)
    }
}