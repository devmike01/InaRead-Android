package dev.gbenga.inaread.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gbenga.inaread.ui.home.HomeScreen
import kotlinx.serialization.Serializable

interface DashboardScreen {

    @Serializable
    data object HomeScreen : DashboardScreen

    @Serializable
    data object Settings : DashboardScreen

    @Serializable
    data object AddReading : DashboardScreen
}

@Composable
fun DashboardScreenNavGraph(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = DashboardScreen.HomeScreen){
        composable<DashboardScreen.HomeScreen> {
            HomeScreen()
        }
    }
}