package dev.gbenga.inaread.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController

interface NavigationDelegate {

    suspend fun handleEvents(navigationEvent: NavigationEvent)
}


class NavigationDelegateImpl(private val navController: NavController) : NavigationDelegate {

    override suspend fun handleEvents(navigationEvent: NavigationEvent) {
        when(navigationEvent){
            is NavigationEvent.Navigate -> {
                navController.navigate(navigationEvent.screen)
            }

            NavigationEvent.NavigateBack -> {
                navController.navigateUp()
            }

            is NavigationEvent.NavigateTaskTop -> {
                navController.navigate(navigationEvent.screen){
                    popUpTo(navigationEvent.screen){
                        inclusive =true
                    }
                }
            }
        }
    }

}

@Composable
fun rememberNavigationDelegate(navController: NavController): NavigationDelegate{
    return remember { NavigationDelegateImpl(navController) }
}