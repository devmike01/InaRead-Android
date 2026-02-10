package dev.gbenga.inaread.utils.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gbenga.inaread.ui.dashboard.DashboardScreenNavGraph
import dev.gbenga.inaread.ui.login.LoginScreen
import dev.gbenga.inaread.ui.signup.SignUpScreen
import dev.gbenga.inaread.ui.theme.InaReadTheme
import dev.gbenga.inaread.ui.usage.AllUnitUsageScreen
import dev.gbenga.inaread.ui.waiting.WaitingScreen
import kotlinx.serialization.Serializable

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
fun InaNavGraph() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = InaScreen.Waiting,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween (300)
            )
        }

    ){
        composable<InaScreen.Dashboard>{
            DashboardScreenNavGraph(parentNavController = navController)
        }

        composable<InaScreen.ForgotPassword> {

        }

        composable<InaScreen.Login> {
            LoginScreen(navController = navController)
        }

        composable<InaScreen.SignUp> {
            SignUpScreen(navController = navController)
        }

        composable<InaScreen.AllUnitUsage> {
            AllUnitUsageScreen(navigator = navController)
        }

        composable<InaScreen.Waiting> {
            WaitingScreen(navHostController = navController)
        }
    }

}

@Composable
fun InaReadApp(){
    InaReadTheme {
        InaNavGraph()
    }
}