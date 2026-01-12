package dev.gbenga.inaread.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.TitledColumn

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val homeUiState by viewModel.state.collectAsStateWithLifecycle()

    HomeScaffold(HomeScaffoldConfig(
        title = StringTokens.DailyActivity,
        navigationClick = {}
    )){
        UnitLaunchEffect {
            viewModel.loadWeekDays()
            viewModel.watchEvents()
        }

        Column(modifier = Modifier.padding(it)
            .fillMaxSize()
            .padding(horizontal = DimenTokens.Padding.normal)) {

            TitledColumn(StringTokens.MeterSummary){
                CalendarTile(homeUiState.weekDates) { date ->
                    viewModel.selectDay(date)
                }
            }

            TitledColumn(StringTokens.MeterSummary){
               // HomeSummaryCard()
            }
        }
    }

}

@Composable
fun UnitLaunchEffect(effect: () -> Unit){
    LaunchedEffect(Unit) {
        effect()
    }
}