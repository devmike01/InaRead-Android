package dev.gbenga.inaread.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ElectricMeter
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.gbenga.inaread.tokens.DimenTokens
import java.util.Locale

@Composable
fun HomeSummaryCard(cardItems: MeterUsageSummary) {

    Card {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(DimenTokens.Padding.small)
                .fillMaxWidth()) {
            cardItems.forEach { item ->
                HomeSummaryCardItem(item)
            }
        }
    }
}


@Composable
fun HomeSummaryCardItem(data: HomeStat){
    Column(modifier = Modifier
        .wrapContentSize()
        .padding(DimenTokens.Padding.small),
        verticalArrangement = Arrangement.spacedBy(DimenTokens.Padding.xSmall),
        horizontalAlignment = Alignment.Start) {
        InaIcon(data = data)

        Text(data.reading.padStart(2, '0'),
            style = MaterialTheme.typography.headlineMedium,)
        Text(data.label, style = MaterialTheme.typography.bodySmall)
    }
}


@Composable
fun InaIcon(data: HomeStat, modifier: Modifier = Modifier){
    when(data){
        is ResIdIconItem -> {
            if (data.icon != null) { Icon(
                    painter = painterResource(data.icon),
                    contentDescription = data.label,
                    modifier = modifier,
                    tint = Color(data.color)
                )
            }
        }
        is VectorItem -> {
            if (data.icon != null)  Icon(imageVector = data.icon,
                contentDescription = data.label,
                modifier = modifier,
                tint = Color(data.color)
            )
        }
        else -> throw IllegalArgumentException("Unsupported version")
    }
}

@Preview
@Composable
fun PreviewHomeSummaryCardItem(){
    HomeSummaryCardItem(VectorItem(
        Icons.Outlined.ElectricMeter,
        "45.9", "Kilowatts",
        color = 0xFFF50057
    ))
}

@Preview
@Composable
fun PreviewHomeSummaryCard(){
    HomeSummaryCard((1..3).map{
        val nextDouble = kotlin.random
            .Random
            .nextDouble(0.1,100.0)
        VectorItem(
            Icons.Outlined.ElectricMeter,
            "%.2f".format(Locale.US, nextDouble)
           , "Kilowatts",
            color = 0xFFF50057
        )
    })
}