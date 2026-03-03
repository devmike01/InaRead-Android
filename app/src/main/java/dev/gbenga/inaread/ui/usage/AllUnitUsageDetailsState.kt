package dev.gbenga.inaread.ui.usage

import dev.gbenga.inaread.utils.InaReadUiState

data class AllUnitUsageDetailsState(
     val uiData: AllUnitUsageDetails = AllUnitUsageDetails()
) : InaReadUiState

data class AllUnitUsageDetails(val meterNo: String="", val category: String="")