package dev.gbenga.inaread.ui.alltime

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.ui.usage.AllUnitUsageUiState
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.InaReadViewModelV2
import javax.inject.Inject

@HiltViewModel
class AllTimeMetricViewModel @Inject constructor() : InaReadViewModelV2<AllUnitUsageUiState> (AllUnitUsageUiState()){


}