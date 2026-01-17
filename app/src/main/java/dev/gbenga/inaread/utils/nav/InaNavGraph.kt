package dev.gbenga.inaread.utils.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gbenga.inaread.ui.dashboard.DashboardScreenNavGraph
import dev.gbenga.inaread.ui.login.LoginScreen
import dev.gbenga.inaread.ui.signup.SignUpScreen
import dev.gbenga.inaread.ui.theme.InaReadTheme
import kotlinx.serialization.Serializable



sealed interface InaScreen {

    @Serializable
    object Dashboard : InaScreen

    @Serializable
    object Login: InaScreen

    @Serializable
    object SignUp: InaScreen

    @Serializable
    object ForgotPassword: InaScreen


}

@Composable
fun InaNavGraph() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = InaScreen.Login){
        composable<InaScreen.Dashboard>{
            DashboardScreenNavGraph()
        }

        composable<InaScreen.ForgotPassword> {

        }

        composable<InaScreen.Login> {
            LoginScreen(navController = navController)
        }

        composable<InaScreen.SignUp> {
            SignUpScreen(navController = navController)
        }
    }

}

@Composable
fun InaReadApp(){
    InaReadTheme {
        InaNavGraph()
    }
}