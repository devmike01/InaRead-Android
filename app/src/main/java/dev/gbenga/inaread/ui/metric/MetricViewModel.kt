package dev.gbenga.inaread.ui.metric

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.data.model.MonthValue
import dev.gbenga.inaread.domain.usecase.GetAppliancesUseCase
import dev.gbenga.inaread.domain.usecase.GetMonthlyChartUseCase
import dev.gbenga.inaread.ui.customs.uiStateWithIdleRunCatching
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.UiStateWithIdle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetricViewModel @Inject constructor(private val getAppliancesUseCase: GetAppliancesUseCase,
                      private val getMonthlyChartUseCase: GetMonthlyChartUseCase) : InaReadViewModel<MetricUiState, MetricEvent>(MetricUiState()) {


    fun populate(){

        viewModelScope.launch {

            val appliances = getAsyncAppliances().await()

            val monthlyChart = getAsyncMonthlyChart().await()

            setState { it.copy(
                monthChartValues = monthlyChart,
                appliances = appliances
            ) }
        }
    }

    fun CoroutineScope.getAsyncMonthlyChart(): Deferred<UiStateWithIdle<List<MonthValue>>> {
        return async { uiStateWithIdleRunCatching {
            getMonthlyChartUseCase()
        } }
    }

    fun CoroutineScope.getAsyncAppliances(): Deferred<UiStateWithIdle<List<Appliance>>>{
        return async { uiStateWithIdleRunCatching{
            getAppliancesUseCase()
        } }
    }
}