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
import dev.gbenga.inaread.utils.nav.DashboardScreen
import dev.gbenga.inaread.utils.nav.InaScreen
import dev.gbenga.inaread.utils.rememberNavigationDelegate
import kotlinx.serialization.Serializable



@Composable
fun DashboardScreenNavGraph(viewModel: DashboardViewModel = hiltViewModel(),){
    val navController = rememberNavController()

    val dashboardUiState by viewModel.state.collectAsStateWithLifecycle()
    val navigatorDelegate = rememberNavigationDelegate(navController)

    Scaffold {

        UnitLaunchEffect {
            viewModel.navigateUsingSavedState()
            viewModel.populateUI()


            viewModel.navigator.collect { nav ->
                navigatorDelegate.handleEvents(nav)
            }
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
                        selected = { route -> dashboardUiState.selectedRoute == route },
                        route = button.route,
                        inaTextIcon = button.inaTextIcon
                    ){ route -> viewModel.gotoNewPage(route) }
                }

            }
        }
    }

}