package dev.gbenga.inaread.ui.waiting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.gbenga.inaread.ui.dialogs.LoadingDialog
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.utils.rememberNavigationDelegate

@Composable
fun WaitingScreen(viewModel: WaitingViewModel = hiltViewModel(), navHostController: NavHostController) {
    val waitingState by viewModel.state.collectAsStateWithLifecycle()
    val navDelegate = rememberNavigationDelegate(navHostController)

    LoadingDialog(waitingState.isLoading)


    UnitLaunchEffect {
        // receive that event and navigate
        viewModel.navigator.collect {
            navDelegate.handleEvents(it)
        }
    }

}