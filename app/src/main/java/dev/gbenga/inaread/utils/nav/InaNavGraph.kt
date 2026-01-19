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

    @Serializable
    object AllUnitUsage : InaScreen
}


@Serializable
sealed interface DashboardScreen : InaScreen {

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
fun InaNavGraph() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = InaScreen.Login,
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
    }

}

@Composable
fun InaReadApp(){
    InaReadTheme {
        InaNavGraph()
    }
}