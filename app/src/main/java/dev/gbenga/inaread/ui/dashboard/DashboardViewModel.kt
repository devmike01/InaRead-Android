package dev.gbenga.inaread.ui.dashboard

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.datastore.Messenger
import dev.gbenga.inaread.ui.home.HomeEvent
import dev.gbenga.inaread.ui.home.HomeViewModel
import dev.gbenga.inaread.ui.home.InaBottomNavItemData
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.nav.DashboardScreen
import dev.gbenga.inaread.utils.nav.toDashboardRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val messenger: Messenger

): InaReadViewModelV2<DashboardUiState>(DashboardUiState()) {


    private val routeStack = Stack<DashboardScreen>()


    fun receiveMessage(){
        viewModelScope.launch {
            //delay(1000)
            messenger.receiveMessage().collect { message ->
                message?.let {
                    showUiMessage(message = message)
                }
            }
        }
    }


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
        ), initialListLoad = true) }
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
        confirmLoad()
        routeStack.push(route)
        setState { it.copy(selectedRoute = route) }
        navigate(NavigationEvent
            .NavigateTaskTop(route))
    }

    fun goBack(){
        if (!routeStack.empty()){
            routeStack.pop()
        }
        setState { it.copy(selectedRoute = if (routeStack.empty()) {
            DashboardScreen.HomeScreen()
        }else routeStack.peek()) }
        navigate(NavigationEvent.NavigateBack)
    }

    fun confirmLoad(){
        if (state.value.initialListLoad){
            setState { it.copy(initialListLoad = false) }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}