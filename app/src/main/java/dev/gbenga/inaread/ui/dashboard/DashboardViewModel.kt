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
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.nav.DashboardScreen
import dev.gbenga.inaread.utils.nav.toDashboardRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): InaReadViewModelV2<DashboardUiState>(DashboardUiState()) {


    fun populateUI(){
        setState { it.copy(dashboardButtons = listOf(
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
        )) }
    }

    fun navigateUsingSavedState(){
        val currentRoute = savedStateHandle
            .get<String>(HomeViewModel.CURRENT_DASHBOARD_ROUTE)
            ?.toDashboardRoute()

        if (currentRoute != DashboardScreen.HomeScreen){
            currentRoute?.let {
                gotoNewPage(it)
            }
        }else{
            // Leave the start destination to handle it
        }
    }

    fun gotoNewPage(route: DashboardScreen){
        setState { it.copy(selectedRoute = route) }
        navigate(NavigationEvent.Navigate(route))
    }


}