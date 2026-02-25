package dev.gbenga.inaread.ui.metric

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.domain.usecase.GetAppliancesUseCase
import dev.gbenga.inaread.ui.customs.uiStateWithIdleRunCatching
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class MetricViewModel @Inject constructor(private val getAppliancesUseCase: GetAppliancesUseCase) : InaReadViewModel<MetricUiState, MetricEvent>(MetricUiState()) {

    init {
        populate()
    }

    fun populate(){
        viewModelScope.launch {
            setState { it.copy(appliances = UiState.Loading) }
            when(val appliances = getAppliancesUseCase()){
                is RepoResult.Success -> {
                    setState { it.copy(
                        appliances = UiState.Success(appliances.data.map { data ->
                            data.copy(rating = data.rating
                                .setScale(2, RoundingMode.HALF_EVEN))
                        })
                    ) }
                }
                is RepoResult.Error -> {
                    setState { it.copy(
                        appliances = UiState.Error(appliances.message)
                    ) }
                }
            }



           // val monthlyChart = getAsyncMonthlyChart().await()

        }
    }


}