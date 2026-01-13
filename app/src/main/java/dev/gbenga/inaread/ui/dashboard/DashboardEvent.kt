package dev.gbenga.inaread.ui.dashboard

sealed interface DashboardEvent  {

    data class GotoPage(val route: DashboardScreen): DashboardEvent

    data class LoadDashboardMenuItems(val selected: Int): DashboardEvent
}