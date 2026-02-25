package dev.gbenga.inaread.ui.usage

import dev.gbenga.inaread.data.model.MonthlyUsage
import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle

data class AllUnitUsageUiState(val monthlyUsageItems: UiState<List<MonthlyUsage>> = UiState.Loading,
    val monthlyUsage: UiStateWithIdle<MonthlyUsage> = UiStateWithIdle.Idle) : InaReadUiState