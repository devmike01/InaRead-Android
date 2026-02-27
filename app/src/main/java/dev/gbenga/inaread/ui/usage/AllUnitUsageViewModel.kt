package dev.gbenga.inaread.ui.usage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.datastore.Messenger
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.MonthlyUsage
import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.domain.usecase.GetAllUnitUsageUseCase
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.date.InaDateFormatter
import dev.gbenga.inaread.utils.ext.naira
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllUnitUsageViewModel @Inject constructor(
    private val getAllUnitUsageUseCase: GetAllUnitUsageUseCase,
    private val inaDateFormatter: InaDateFormatter,
    private val messenger: Messenger
) : InaReadViewModelV2<AllUnitUsageUiState> (AllUnitUsageUiState()){


    init {
        fetchUnitUsages()
        receiveMessage()
    }

    fun receiveMessage(){
        viewModelScope.launch {
            messenger.receiveMessage().collect {
                showUiMessage(it)
            }
        }
    }

    fun showSnackBarMessage(message: String){
       viewModelScope.launch {
           messenger.sendMessage(message = message)
       }
    }

    fun fetchUnitUsages(){
        setState { it.copy(monthlyUsageItems = UiState.Loading) }
        viewModelScope.launch {
            val monthlyUsageUiState = when(val repoResult = getAllUnitUsageUseCase()){
                is RepoResult.Success -> {
                    UiState.Success(repoResult.data.map {
                        val fromDates = inaDateFormatter
                            .ddMMM(it.fromDate)
                            .split("/")

                        MonthlyUsage(
                            kilowatt = it.totalMonthPowerUsage.toPlainString(),
                            period = it.periodInDays,
                            cost = it.totalSpent.naira(),
                            dayOfMonth = fromDates[0],
                            day = fromDates.first(),
                            month = fromDates.last(),
                            fromDate = it.fromDate,
                            toDate = it.toDate,
                            meterType = it.meterType
                        )
                    })
                }
                is RepoResult.Error -> {
                    UiState.Error(repoResult.message)
                }
            }

            setState { it.copy(
                monthlyUsageItems = monthlyUsageUiState,
            ) }
        }
    }

    fun setUnitUsage(monthlyUsageRequest: MonthlyUsageRequest){
        setState { it.copy(monthlyUsage = UiStateWithIdle.Loading) }
        viewModelScope.launch {
//            val monthlyUsageUiState = uiStateWithIdleRunCatching {
//                setAllUnitUsageUseCase(monthlyUsageRequest)
//            }
//            setState { it.copy(monthlyUsage = monthlyUsageUiState) }
        }
    }

    fun goBack(){
        navigate(NavigationEvent.NavigateBack)
    }

}