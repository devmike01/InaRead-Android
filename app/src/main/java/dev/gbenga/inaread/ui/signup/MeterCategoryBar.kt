package dev.gbenga.inaread.ui.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gbenga.inaread.tokens.DimenTokens

@Composable
fun MeterCategoryBar(
    selectedId: Int,
    categories: List<MeterCategory>,
                     onSelect: (Int) -> Unit) {

    LazyRow(modifier = Modifier.height(DimenTokens.Button.Normal),
        horizontalArrangement = Arrangement.spacedBy(DimenTokens.Padding.XSmall)) {
        items(categories.size,){
            MeterCategoryItem(selectedId,
                categories[it], onSelect)
        }
    }
}

@Composable
fun MeterCategoryItem(
    selectedId: Int,
    meterCategory: MeterCategory,
                      onSelect: (Int) -> Unit){
    OutlinedButton(onClick = {
        onSelect(meterCategory.id)
    }, colors = ButtonDefaults.outlinedButtonColors(
        containerColor = MaterialTheme
            .colorScheme.primary.takeIf { selectedId == meterCategory.id }
            ?: Color.Transparent
    ), border = BorderStroke(1.dp,
        MaterialTheme
            .colorScheme.primary.takeIf { selectedId == meterCategory.id }
            ?: Color.Gray)) {
        Text("Band ${meterCategory.name}")
    }
}

@Preview
@Composable
fun PreviewMeterCategoryBar(){
    var meterCategoryState by remember{ mutableStateOf(0) }
    MeterCategoryBar(meterCategoryState,
        listOf("A", "B", "C", "D", "E").mapIndexed { index, band ->   MeterCategory(name = band, id = index) }){
2
    }
}