package dev.gbenga.inaread.utils.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gbenga.inaread.ui.dashboard.DashboardScreenNavGraph
import kotlinx.serialization.Serializable



sealed interface InaScreen {

    @Serializable
    object Dashboard : InaScreen

    @Serializable
    object Profile: InaScreen

    @Serializable
    object Login: InaScreen

    @Serializable
    object SignUp: InaScreen
}

@Composable
fun InaNavGraph() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = InaScreen.Dashboard){
        composable<InaScreen.Dashboard>{
            DashboardScreenNavGraph()
        }

        composable<InaScreen.Profile> {

        }

        composable<InaScreen.Login> {

        }

        composable<InaScreen.SignUp> {

        }
    }

}

@Composable
fun InaReadApp(){
    InaNavGraph()
}