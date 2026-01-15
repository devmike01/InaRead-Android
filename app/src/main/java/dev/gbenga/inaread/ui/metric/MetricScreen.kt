package dev.gbenga.inaread.ui.metric

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.gbenga.inaread.data.model.MonthValue
import dev.gbenga.inaread.ui.customs.BarChart
import dev.gbenga.inaread.ui.home.HomeParentColumn
import dev.gbenga.inaread.ui.home.UnitLaunchEffect

@Composable
fun MetricScreen(viewModel: MetricViewModel = hiltViewModel()) {
    HomeParentColumn("Metrics", subTitle = "Key Performance Overview",
        modifier = Modifier.fillMaxSize()) {

        UnitLaunchEffect {
            viewModel.watchEvents()
            viewModel.populate()
        }

        BarChart(emptyList())
    }
}



@Preview
@Composable
fun PreviewMetricScreen(){

}