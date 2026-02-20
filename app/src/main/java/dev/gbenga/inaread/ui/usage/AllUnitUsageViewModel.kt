package dev.gbenga.inaread.ui.usage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.domain.usecase.GetAllUnitUsageUseCase
import dev.gbenga.inaread.domain.usecase.SetAllUnitUsageUseCase
import dev.gbenga.inaread.ui.customs.uiStateWithIdleRunCatching
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.Scada
import dev.gbenga.inaread.utils.UiStateWithIdle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllUnitUsageViewModel @Inject constructor(private val getAllUnitUsageUseCase: GetAllUnitUsageUseCase,
    private val setAllUnitUsageUseCase: SetAllUnitUsageUseCase) : InaReadViewModelV2<AllUnitUsageUiState> (AllUnitUsageUiState()){


    fun fetchUnitUsages(){
        setState { it.copy(monthlyUsageItems = UiStateWithIdle.Loading) }
        viewModelScope.launch {
            val monthlyUsageUiState = uiStateWithIdleRunCatching {
                getAllUnitUsageUseCase()
            }
            Scada.info("monthlyUsageUiState -> $monthlyUsageUiState")
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