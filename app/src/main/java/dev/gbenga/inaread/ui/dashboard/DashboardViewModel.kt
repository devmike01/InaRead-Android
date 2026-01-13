package dev.gbenga.inaread.ui.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.ui.home.HomeEvent
import dev.gbenga.inaread.ui.home.HomeViewModel
import dev.gbenga.inaread.ui.home.InaBottomNavItemData
import dev.gbenga.inaread.utils.InaReadViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): InaReadViewModel<DashboardUiState, DashboardEvent>(DashboardUiState()) {


    companion object{
        const val CURRENT_DASHBOARD_ROUTE = "HomeViewModel.CURRENT_DASHBOARD_ROUTE"
    }


    fun gotoRoute(route: DashboardScreen){
        sendEvent(DashboardEvent.GotoPage(route))
    }

    fun populateUI(){
        sendEvent(DashboardEvent.LoadDashboardMenuItems(0))
    }

    fun navigateUsingSavedState(){
        val currentRoute = savedStateHandle
            .get<String>(HomeViewModel.CURRENT_DASHBOARD_ROUTE)
            ?.toDashboardRoute()

        if (currentRoute != DashboardScreen.HomeScreen){
            currentRoute?.let {
                sendEvent(DashboardEvent.GotoPage(it))
            }
        }else{
            // Leave the start destination to handle it
        }
    }

    override fun watchEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when(event){
                    is DashboardEvent.GotoPage -> {
                        savedStateHandle[CURRENT_DASHBOARD_ROUTE] = event.route.key
                        setState{
                            it.copy(
                                route = event.route
                            )
                        }
                    }

                    is DashboardEvent.LoadDashboardMenuItems -> setState {
                        it.copy(dashboardButtons = listOf(
                            DashboardNavData(
                                route = DashboardScreen.HomeScreen(),
                                inaTextIcon = InaBottomNavItemData(
                                    icon = Icons.Outlined.Home,
                                    label = "Home",
                                )
                            ),
                            DashboardNavData(
                                route = DashboardScreen.AddReading(),
                                inaTextIcon = InaBottomNavItemData(
                                    icon = Icons.Default.Add,
                                    label = "Add",
                                )
                            ),
                            DashboardNavData(
                                route = DashboardScreen.AllTimeUsage(),
                                inaTextIcon = InaBottomNavItemData(
                                    icon = Icons.Default.BarChart,
                                    label = "All time",
                                )
                            ),
                            DashboardNavData(
                                route = DashboardScreen.Settings(),
                                inaTextIcon = InaBottomNavItemData(
                                    icon = Icons.Outlined.Settings,
                                    label = "Settings",
                                )
                            )
                        ))
                    }
                }
            }
        }
    }

}