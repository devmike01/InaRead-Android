package dev.gbenga.inaread.ui.dashboard

import dev.gbenga.inaread.ui.home.InaBottomNavItemData
import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.nav.DashboardScreen

data class DashboardUiState (
    val selectedRoute: DashboardScreen = DashboardScreen.HomeScreen(),
    val dashboardButtons: List<DashboardNavData> = emptyList()
): InaReadUiState

data class DashboardNavData(
    val route: DashboardScreen,
    val inaTextIcon : InaBottomNavItemData,
    )