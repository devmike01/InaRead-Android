package dev.gbenga.inaread.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.FailedUiStateComponent
import dev.gbenga.inaread.ui.customs.TitledColumn
import dev.gbenga.inaread.ui.customs.UiStateWithLoadingBox
import dev.gbenga.inaread.utils.rememberNavigationDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel,
               parentNavController: NavController,
               showSnackBarMessage: (String) -> Unit) {
    val homeUiState by viewModel.state.collectAsStateWithLifecycle()

    UnitLaunchEffect {
        viewModel.snackBarEvent.collect { message ->
            showSnackBarMessage(message)
        }
    }

    UnitLaunchEffect {
        viewModel.receiveMessage()
    }


    HomeScaffold(HomeScaffoldConfig(
        title = homeUiState.todaysDate,
        subTitle = homeUiState.greeting,
        navigationClick = {}
    ), profileInitial = homeUiState.nameInitial){

        val navigatorDelegate = rememberNavigationDelegate(parentNavController)

        UnitLaunchEffect {
            launch {
                viewModel.navigator.collect {
                    navigatorDelegate.handleEvents(it)
                }
            }
        }

        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = DimenTokens.Padding.Normal,
                vertical = DimenTokens.Padding.Normal)) {

           item {
               TitledColumn(StringTokens.MeterSummary,
                   endText = "View All", onEndTextClick = {
                       viewModel.gotoAllUnitUsage()
                   }){
                   CalendarTile(
                       homeUiState.daysOfMonth,
                       homeUiState.selectedCalendarPos,
                       isAvailable = false) { dayOfMonth, index ->
                       viewModel.selectNewDay(dayOfMonth,index)
                   }
               }
           }

            item {
                TitledColumn(StringTokens.MeterSummary,
                    modifier = Modifier.padding(vertical = DimenTokens.Padding.Large).animateItem()){
                    UiStateWithLoadingBox(homeUiState.meterUsageSummary,
                        errorRequest = { error ->
                            FailedUiStateComponent(error){
                              //  viewModel.
                            }
                        }){ homeSummary ->
                        HomeSummaryCard(homeSummary.second,
                            homeUiState.selectedDateValue)
                    }

                }
            }

            item {
                TitledColumn(StringTokens.MonthlyUsageSummary,
                    modifier = Modifier.animateItem()){
                    UiStateWithLoadingBox(homeUiState.meterUsageSummary,
                        errorRequest = { error ->
                            FailedUiStateComponent(error)
                        }){ homeSummary ->
                        LifeTimeStats(homeSummary.first)
                    }

                }
                Spacer(Modifier.height(DimenTokens.Padding.XLarge))
            }
        }
    }

}

@Composable
fun UnitLaunchEffect(block: suspend CoroutineScope.() -> Unit){
    LaunchedEffect(Unit) {
        block()
    }
}
