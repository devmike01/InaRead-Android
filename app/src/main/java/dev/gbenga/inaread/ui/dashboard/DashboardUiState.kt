package dev.gbenga.inaread.ui.dashboard

import dev.gbenga.inaread.ui.home.InaBottomNavItemData
import dev.gbenga.inaread.utils.InaReadUiState

data class DashboardUiState (
    val route: DashboardScreen = DashboardScreen.HomeScreen(),
    val dashboardButtons: List<DashboardNavData> = emptyList()
): InaReadUiState

data class DashboardNavData(
    val route: DashboardScreen,
    val inaTextIcon : InaBottomNavItemData,
    )