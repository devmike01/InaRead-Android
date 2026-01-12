package dev.gbenga.inaread.ui.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.DimenTokens.CalendarItem
import dev.gbenga.inaread.tokens.DimenTokens.Radius
import dev.gbenga.inaread.ui.customs.HorizontalCenter
import dev.gbenga.inaread.ui.theme.DeepOrange

@Composable
fun CalendarTile(weekDays: CalendarTileWeekDays, onItemClick: (Int) -> Unit) {

    LazyRow(modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(CalendarItem.outerPadding)) {
        items(weekDays.size){ index ->
            val weekDay = weekDays[index]
             CalendarTileItem(weekDay, onItemClick = {
                onItemClick(index)
            })
        }
    }
}

@Composable
fun CalendarTileItem(item: CalendarTileData,
                     onItemClick: () -> Unit){
    Button(
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp
        ),
        onClick = onItemClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = DeepOrange.takeIf { item.selected } ?: MaterialTheme.colorScheme
                .secondary,
            contentColor = MaterialTheme.typography.bodyMedium.color
        ),
        contentPadding = PaddingValues(DimenTokens.CalendarItem.padding),
        modifier = Modifier
            .height(CalendarItem.height)
            .width(CalendarItem.width)
            .padding(DimenTokens.Padding.xSmall),
        shape = RoundedCornerShape(Radius.small)) {
        HorizontalCenter() {
            Text(text =item.month,
                style = MaterialTheme.typography.bodyMedium
                    .copy(fontWeight = FontWeight.W800))
            Text(text =item.day.padStart(2, '0'),
                style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview
@Composable
fun PreviewCalendarTileItem(){
    CalendarTileItem(
        CalendarTileData(
            month = "Jan",
            day = "12",
            dateInMillis = 12122L,
            selected = false
        )
    ){}
}

@Preview(device = "id:pixel_9_pro", name = "pixel9Pro",
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewCalendarTile(){
    CalendarTile( (1..10).mapIndexed { id, i ->
        CalendarTileData(
            month = "Mon$i",
            day = "$i",
            dateInMillis = 12122L,
            selected = i == 2
        )
    }, onItemClick = {})
}