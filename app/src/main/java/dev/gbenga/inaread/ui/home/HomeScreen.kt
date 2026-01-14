package dev.gbenga.inaread.ui.home

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
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.FailedUiStateComponent
import dev.gbenga.inaread.ui.customs.TitledColumn
import dev.gbenga.inaread.ui.customs.UiStateWithLoadingBox
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val homeUiState by viewModel.state.collectAsStateWithLifecycle()

    HomeScaffold(HomeScaffoldConfig(
        title = homeUiState.todaysDate,
        subTitle = homeUiState.greeting,
        navigationClick = {}
    )){
        UnitLaunchEffect {
            viewModel.watchEvents()
            viewModel.populateDashboard()
        }

        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = DimenTokens.Padding.normal,
                vertical = DimenTokens.Padding.normal)) {

           item {
               TitledColumn(StringTokens.MeterSummary,
                   modifier = Modifier.animateItem()){
                   CalendarTile(
                       homeUiState.daysOfMonth,
                       homeUiState.selectedCalendarPos,
                       isAvailable = false) { dayOfMonth, index ->
                       viewModel.selectDay(dayOfMonth,index)
                   }
               }
           }

            item {
                TitledColumn(StringTokens.MeterSummary,
                    modifier = Modifier.padding(vertical = DimenTokens.Padding.large).animateItem()){
                    UiStateWithLoadingBox(homeUiState.meterUsageSummary,
                        errorRequest = { error ->
                            FailedUiStateComponent(error)
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
                Spacer(Modifier.height(DimenTokens.Padding.xLarge))
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

@Composable
fun <T> ValueLaunchEffect(key: T, block: suspend CoroutineScope.(T) -> Unit){
    LaunchedEffect(key) {
        block(key)
    }
}