package dev.gbenga.inaread.ui.dashboard

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.addreading.AddReadingScreen
import dev.gbenga.inaread.ui.addreading.AddReadingViewModel
import dev.gbenga.inaread.ui.home.HomeBottomNav
import dev.gbenga.inaread.ui.home.HomeScreen
import dev.gbenga.inaread.ui.home.HomeViewModel
import dev.gbenga.inaread.ui.home.InaBottomNavItem
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.ui.metric.MetricScreen
import dev.gbenga.inaread.ui.settings.SettingsScreen
import dev.gbenga.inaread.ui.settings.SettingsViewModel
import dev.gbenga.inaread.ui.theme.Dark
import dev.gbenga.inaread.utils.nav.DashboardScreen
import dev.gbenga.inaread.utils.rememberNavigationDelegate
import kotlinx.coroutines.launch


@Composable
fun DashboardScreenNavGraph(viewModel: DashboardViewModel,
                            parentNavController: NavController){
    val navController = rememberNavController()
    val snackbarHost = remember { SnackbarHostState() }
    val navigatorDelegate = rememberNavigationDelegate(navController)
    val startDestination = remember { DashboardScreen.HomeScreen() }

    UnitLaunchEffect {
        viewModel.navigator.collect { nav ->
            navigatorDelegate.handleEvents(nav)
        }
    }


    LaunchedEffect(Unit) {
        launch {
            viewModel.navigateUsingSavedState()
            viewModel.populateUI()
        }
        launch {
            viewModel.receiveMessage()
        }
    }

    UnitLaunchEffect {
        viewModel.snackBarEvent.collect { message ->
            snackbarHost.showSnackbar(message)
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHost){
                Snackbar(
                    containerColor = Dark,
                    snackbarData = it
                )
            }
        }
    ) {

        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize(),) {

            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val homeViewModel: HomeViewModel = hiltViewModel()
            val addReadingViewModel: AddReadingViewModel = hiltViewModel()

            NavHost(navController, startDestination = startDestination){
                composable<DashboardScreen.HomeScreen> {

                    var message by remember { mutableStateOf<String?>(null) }

                    message?.let { message ->
                        LaunchedEffect(message) {
                            Log.d("message001", "message: $message")
                            snackbarHost.showSnackbar(message)
                        }
                    }

                    HomeScreen(homeViewModel,
                        parentNavController = parentNavController,
                        showSnackBarMessage = { msg ->
                            if (msg.isNotBlank()){
                                message = msg
                            }
                        }
                        )
                }

                composable<DashboardScreen.AddReading> {
                    AddReadingScreen(addReadingViewModel, dashboardNavController = navController)
                }

                composable<DashboardScreen.Settings> {
                    SettingsScreen(settingsViewModel, navHostController = parentNavController)
                }

                composable<DashboardScreen.AllTimeUsage> {
                    MetricScreen()
                }
            }
            PageHandler(viewModel)
        }

        BackHandler() {
            viewModel.goBack()
        }
    }

}

@Composable
fun BoxScope.PageHandler(dashboardViewModel: DashboardViewModel){


    val dashboardUiState by dashboardViewModel.state.collectAsStateWithLifecycle()

    HomeBottomNav(
        modifier = Modifier
            .fillMaxWidth(.85f)
            .align(Alignment.BottomCenter)
            .padding(vertical = DimenTokens.Padding.Normal),
    ){

        if (dashboardUiState.initialListLoad){
            dashboardUiState.dashboardButtons.forEach { button ->

                }
            }
        this.items(items = dashboardUiState.dashboardButtons,
            key = {
                it.route.key
            }){ button ->
            InaBottomNavItem(
                selected = dashboardUiState.selectedRoute == button.route,
                inaTextIcon = button.inaTextIcon
            ){  dashboardViewModel.gotoNewPage(button.route) }

        }


    }
}