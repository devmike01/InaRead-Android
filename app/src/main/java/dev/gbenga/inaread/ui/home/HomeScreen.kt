package dev.gbenga.inaread.ui.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import dev.gbenga.inaread.tokens.StringTokens

@Composable
fun HomeScreen() {

    HomeScaffold(HomeScaffoldConfig(
        title = StringTokens.DailyActivity,
        navigationClick = {}
    )){

    }


}