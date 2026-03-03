package dev.gbenga.inaread.utils.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.gbenga.inaread.data.model.MonthlyUsage
import dev.gbenga.inaread.ui.dashboard.DashboardScreenNavGraph
import dev.gbenga.inaread.ui.dashboard.DashboardViewModel
import dev.gbenga.inaread.ui.login.LoginScreen
import dev.gbenga.inaread.ui.signup.SignUpScreen
import dev.gbenga.inaread.ui.signup.SignUpViewModel
import dev.gbenga.inaread.ui.theme.InaReadTheme
import dev.gbenga.inaread.ui.usage.AllUnitUsageDetails
import dev.gbenga.inaread.ui.usage.AllUnitUsageScreen
import dev.gbenga.inaread.ui.waiting.WaitingScreen
import kotlin.reflect.typeOf

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
    val dashboardNagGraphVM: DashboardViewModel = hiltViewModel()
    val signUpViewModel: SignUpViewModel = hiltViewModel()

    NavHost(navController, startDestination = InaScreen.Waiting,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
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
            DashboardScreenNavGraph(dashboardNagGraphVM, parentNavController = navController)
        }

        composable<InaScreen.ForgotPassword> {

        }

        composable<InaScreen.Login> {
            LoginScreen(navController = navController)
        }

        composable<InaScreen.SignUp> {
            SignUpScreen(navController = navController,
                signUpViewModel = signUpViewModel)
        }

        composable<InaScreen.AllUnitUsage> {
            AllUnitUsageScreen(navigator = navController)
        }

        composable<InaScreen.Waiting> {
            WaitingScreen(navHostController = navController)
        }

        composable<InaScreen.AllUnitUsageDetailsScreen>(
            typeMap = mapOf(typeOf<MonthlyUsage>() to MonthlyUsageType)
        ) {
            val usageDetails = it.toRoute<InaScreen.AllUnitUsageDetailsScreen>()
            AllUnitUsageDetails(usageDetails.monthlyUsage,
                onNavBack = {
                    navController.popBackStack()
                })
        }
    }

}

@Composable
fun InaReadApp(){
    InaReadTheme {
        InaNavGraph()
    }
}