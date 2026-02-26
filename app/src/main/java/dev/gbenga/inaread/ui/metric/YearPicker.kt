package dev.gbenga.inaread.ui.metric

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.utils.Scada
import java.util.Calendar


class YearPickerState{

    var state by mutableStateOf(0)
    var showAllYears by mutableStateOf(false)


    private val MAX_YEAR =  Calendar.getInstance().get(Calendar.YEAR) //- 1900

    companion object{
        const val MIN_YEAR = 2020
    }


    fun select(year: Int){
        state = year
        Scada.info("YearPickerState: $year")
    }

    fun showYears(){
        showAllYears = true
    }

    fun hideYears(){
        showAllYears = false
    }

    fun select(year: String){
        select(year.toIntOrNull() ?: MIN_YEAR)
        hideYears()
    }

    fun nextYear(){
        state += if(state < MAX_YEAR){
            1
        } else{
            0
        }
    }

    fun previousYear(){
        state -= if(state > MIN_YEAR){
            1
        }else{
            0
        }
    }

    val allYears: List<String> = (MIN_YEAR..MAX_YEAR).map { it.toString() }

}

@Composable
fun rememberYearPickerState(): YearPickerState{
    val year = Calendar.getInstance().get(Calendar.YEAR) //- 1900
    return remember {
        YearPickerState().apply {
            select(year)
        }
    }
}


@Composable
fun YearPicker(yearState: YearPickerState,
               modifier: Modifier = Modifier,
               onYearClick: (String) -> Unit) {


    Column(modifier = Modifier.wrapContentHeight()
        .padding(bottom = DimenTokens.Padding.Normal)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .clickable{
                    if(yearState.showAllYears){
                        yearState.hideYears()
                    }else yearState.showYears()
                }) {
            ArrowButton(Icons.AutoMirrored.Default.ArrowBack,
                StringTokens.YearPicker.ArrowBack) {
                yearState.previousYear()
            }
            Text(yearState.state.toString(), style = MaterialTheme.typography.titleLarge)
            ArrowButton(Icons.AutoMirrored.Default.ArrowForward,
                StringTokens.YearPicker.ArrowForward) {
                yearState.nextYear()
            }
        }


        AnimatedVisibility(yearState.showAllYears) {

            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.FixedSize(80.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {

                yearState.allYears.forEach { year ->
                    item(
                        key = year,) {
                        TextButton(onClick = {
                            yearState.select(year)
                            onYearClick(year)
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent.takeIf {
                                    yearState.state.toString() != year

                                } ?: Color(0x77FF5722)
                            ),
                            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.tertiary),
                            shape = RoundedCornerShape(DimenTokens.Radius.large),
                            modifier = Modifier
                                .padding(DimenTokens.Padding.XSmall)
                                .wrapContentHeight()
                                .padding(3.dp)
                        ) {
                            Text(year)
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun ArrowButton(icon: ImageVector,
                contentDescription: String,
                onClick: () -> Unit){

    IconButton(onClick) {
        Icon( icon,
            contentDescription = contentDescription)
    }
}

@Preview
@Composable
fun PreviewYearPicker(){
    val yearState = rememberYearPickerState()
    yearState.showYears()
    YearPicker(yearState){

    }
}