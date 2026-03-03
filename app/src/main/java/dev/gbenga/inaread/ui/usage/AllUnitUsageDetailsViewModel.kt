package dev.gbenga.inaread.ui.usage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.domain.usecase.GetProfileUseCase
import dev.gbenga.inaread.utils.InaReadViewModelV2
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllUnitUsageDetailsViewModel @Inject constructor(val getProfileUseCase: GetProfileUseCase)
    : InaReadViewModelV2<AllUnitUsageDetailsState>(AllUnitUsageDetailsState()) {

        init {
            getUserMeterDetails()
        }

    fun getUserMeterDetails(){
       viewModelScope.launch {
           setState { it.copy(uiData = AllUnitUsageDetails(
               meterNo = "Loading...",
               category = "Loading..." //profile.data.meterCategoryId
           )) }
           when(val profile = getProfileUseCase()){
               is RepoResult.Success -> {
                   setState { state -> state.copy(uiData = AllUnitUsageDetails(
                       meterNo = profile.data.meterNo,
                       category = "Band A" //profile.data.meterCategoryId
                   )) }
               }
               is RepoResult.Error -> {
                   setState { it.copy(uiData = AllUnitUsageDetails(
                       meterNo = "na",
                       category = "na" //profile.data.meterCategoryId
                   )) }
               }
           }

       }
    }

}