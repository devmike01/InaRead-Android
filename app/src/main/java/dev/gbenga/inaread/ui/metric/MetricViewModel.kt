package dev.gbenga.inaread.ui.metric

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.Appliance
import dev.gbenga.inaread.data.model.MonthValue
import dev.gbenga.inaread.domain.usecase.GetAppliancesUseCase
import dev.gbenga.inaread.domain.usecase.GetYearlyUsageUseCase
import dev.gbenga.inaread.ui.customs.uiStateWithIdleRunCatching
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.date.InaDateFormatter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class MetricViewModel @Inject constructor(
    private val getAppliancesUseCase: GetAppliancesUseCase,
    private val getYearlyUsageUseCase: GetYearlyUsageUseCase,
    private val inaDateFormatter: InaDateFormatter
    )
    : InaReadViewModelV2<MetricUiState>(MetricUiState()) {

    private var cacheChartList : List<MonthValue>? = null


    init {
        populate()

    }

    fun getYearlyUsage(year: Int){
        if (cacheChartList != null) return
        setState { it.copy(monthChartValues = UiStateWithIdle.Loading) }
        viewModelScope.launch {
            when(val usage = getYearlyUsageUseCase(year)){
                is RepoResult.Success -> {
                    val distinctMap = mutableMapOf<String, Float>()
                    for (yearlyResponse in  usage.data){
                        val month = inaDateFormatter.mMM(yearlyResponse.fromDate) ?: "n/a"
                        val consumption = yearlyResponse.totalMonthPowerUsage.toFloat()
                        distinctMap.merge(month, consumption, Float::plus)
                    }


                        setState {
                        it.copy(monthChartValues = UiStateWithIdle.Success(
                            distinctMap.map { month -> MonthValue(month.key, month.value) }.apply {
                                cacheChartList = this
                            }
                        ))
                    }
                }
                is RepoResult.Error -> {
                    setState { it.copy(monthChartValues = UiStateWithIdle.Error(usage.message)) }
                }
            }
        }
    }

    fun populate(){
        viewModelScope.launch {
            setState { it.copy(appliances = UiState.Loading) }
            when(val appliances = getAppliancesUseCase()){
                is RepoResult.Success -> {
                    setState { it.copy(
                        appliances = UiState.Success(appliances.data.map { data ->
                            data.copy(rating = data.rating
                                .setScale(2, RoundingMode.HALF_EVEN),
                                createdOn = inaDateFormatter.dddMMMYyyy(data.createdOn)
                                )
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