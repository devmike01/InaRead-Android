package dev.gbenga.inaread.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.home.HomeBottomNav
import dev.gbenga.inaread.ui.home.HomeScreen
import dev.gbenga.inaread.ui.home.InaBottomNavItem
import dev.gbenga.inaread.ui.home.InaBottomNavItemData
import kotlinx.serialization.Serializable


@Serializable
sealed interface DashboardScreen {

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

    Scaffold {
        Box(modifier = Modifier.padding(it),) {

            NavHost(navController, startDestination = DashboardScreen.HomeScreen){
                composable<DashboardScreen.HomeScreen> {
                    HomeScreen()
                }
            }

            HomeBottomNav(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .align(Alignment.BottomCenter)
                    .padding(vertical = DimenTokens.Padding.normal),
            ){
                InaBottomNavItem(
                    selected = false,
                    route = DashboardScreen.HomeScreen,
                    inaTextIcon = InaBottomNavItemData(
                        icon = Icons.Default.BarChart,
                        label = "Home",
                    )
                ){}

                InaBottomNavItem(
                    selected = false,
                    route = DashboardScreen.AddReading,
                    inaTextIcon = InaBottomNavItemData(
                        icon = Icons.Default.BarChart,
                        label = "Add",
                    )
                ){}

                InaBottomNavItem(
                    selected = true,
                    route = DashboardScreen.Settings,
                    inaTextIcon = InaBottomNavItemData(
                        icon = Icons.Default.Settings,
                        label = "Settings",
                    )
                ){}
            }
        }
    }

}