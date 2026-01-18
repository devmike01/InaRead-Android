package dev.gbenga.inaread.ui.dashboard

@Deprecated("Marked for removal because of internal loop in viewmodel")
sealed interface DashboardEvent  {


    data class LoadDashboardMenuItems(val selected: Int): DashboardEvent
}