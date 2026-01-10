package dev.gbenga.inaread.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.DimenTokens.CalendarItem
import dev.gbenga.inaread.tokens.DimenTokens.Radius
import dev.gbenga.inaread.ui.customs.HorizontalCenter

@Composable
fun CalendarTile(weekDays: CalendarTileWeakDays, onItemClick: (Int) -> Unit) {

    LazyRow(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(CalendarItem.outerPadding)) {
        items(weekDays.size){ index ->
            val weekDay = weekDays[index]
            CalendarTileItem(weekDay, onItemClick = onItemClick)
        }
    }
}

@Composable
fun CalendarTileItem(item: CalendarTileData, onItemClick: (Int) -> Unit){
    Button(onClick = {
        onItemClick(item.id)
    },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.typography.bodyMedium.color
        ),
        contentPadding = PaddingValues(DimenTokens.CalendarItem.padding),
        modifier = Modifier.height(DimenTokens.CalendarItem.height)
            .width(DimenTokens.CalendarItem.width),
        shape = RoundedCornerShape(Radius.small)) {
        HorizontalCenter() {
            Text(text =item.month,
                style = MaterialTheme.typography.bodySmall)
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
            id = 0
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
            id = id
        )
    }, onItemClick = {})
}