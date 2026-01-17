package dev.gbenga.inaread.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.addreading.AddReadingScreen
import dev.gbenga.inaread.ui.home.HomeBottomNav
import dev.gbenga.inaread.ui.home.HomeScreen
import dev.gbenga.inaread.ui.home.InaBottomNavItem
import dev.gbenga.inaread.ui.home.InaBottomNavItemData
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.ui.home.ValueLaunchEffect
import dev.gbenga.inaread.ui.metric.MetricScreen
import dev.gbenga.inaread.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable


@Serializable
sealed interface DashboardScreen {

    val key: String

    companion object{
        const val HOME_KEY = "DashboardScreen.HOME"
        const val SETTINGS_KEY = "DashboardScreen.SETTINGS_KEY"
        const val ADD_READING_KEY = "DashboardScreen.ADD_READING"
        const val ALL_TIME_USAGE = "DashboardScreen.ALL_TIME_USAGE"
    }

    @Serializable
    data class HomeScreen(override val key: String = HOME_KEY) : DashboardScreen

    @Serializable
    data class Settings(override val key: String= SETTINGS_KEY) : DashboardScreen

    @Serializable
    data class AllTimeUsage(override val key: String= ALL_TIME_USAGE) : DashboardScreen

    @Serializable
    data class AddReading(override val key: String= ADD_READING_KEY) : DashboardScreen
}

fun String?.toDashboardRoute(): DashboardScreen{
    return when(this){
        DashboardScreen.ADD_READING_KEY -> DashboardScreen.AddReading(this)
        DashboardScreen.HOME_KEY -> DashboardScreen.HomeScreen(this)
        DashboardScreen.SETTINGS_KEY ->  DashboardScreen.HomeScreen(this)
        DashboardScreen.ALL_TIME_USAGE -> DashboardScreen.AllTimeUsage(this)
        else -> throw IllegalArgumentException("Argument is not supported")
    }
}

@Composable
fun DashboardScreenNavGraph(viewModel: DashboardViewModel = hiltViewModel()){
    val navController = rememberNavController()

    val dashboardUiState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold {

        UnitLaunchEffect {
            viewModel.watchEvents()
            viewModel.navigateUsingSavedState()
            viewModel.populateUI()
        }

        ValueLaunchEffect(dashboardUiState.route) { route ->
            navController.navigate(route)
        }

        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize(),) {

            NavHost(navController, startDestination = DashboardScreen.HomeScreen()){
                composable<DashboardScreen.HomeScreen> {
                    HomeScreen()
                }

                composable<DashboardScreen.AddReading> {
                    AddReadingScreen()
                }

                composable<DashboardScreen.Settings> {
                    SettingsScreen()
                }

                composable<DashboardScreen.AllTimeUsage> {
                    MetricScreen()
                }
            }

            HomeBottomNav(
                modifier = Modifier
                    .fillMaxWidth(.85f)
                    .align(Alignment.BottomCenter)
                    .padding(vertical = DimenTokens.Padding.normal),
            ){

                dashboardUiState.dashboardButtons.forEach { button ->
                    InaBottomNavItem(
                        selected = { route -> dashboardUiState.route == route },
                        route = button.route,
                        inaTextIcon = button.inaTextIcon
                    ){ route -> viewModel.gotoRoute(route) }
                }

            }
        }
    }

}