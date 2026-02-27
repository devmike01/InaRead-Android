package dev.gbenga.inaread.ui.usage

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gbenga.inaread.R
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.InaCard
import dev.gbenga.inaread.ui.customs.TitledColumn
import dev.gbenga.inaread.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllUnitUsageDetails() {
    val verticalScrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Usage Details")
            },
                actions = {
                    Icon(Icons.Default.Delete,
                        contentDescription = "Delete")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back")
                    }
                })
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .padding(DimenTokens.Padding.Normal)
            .verticalScroll(verticalScrollState)) {
            
            UsedKwHComponent("23.21", "3,234")
            TitledColumn("Other Details",
                modifier = Modifier.padding(top = DimenTokens.Padding.Normal)) {
                OtherDetailsItem("From", "Aug 12, 2026")
                OtherDetailsItem("To", "Aug 19, 2026")
                OtherDetailsItem("Meter Type", "Prepaid")
                OtherDetailsItem("Meter No.", "12542920")
                OtherDetailsItem("Category", "Band A")
            }
        }
    }
}

@Composable
fun OtherDetailsItem(title: String, subTitle: String){
    InaCard(modifier = Modifier
        .fillMaxWidth(),) {
        Column(modifier = Modifier.padding(DimenTokens.Padding.Normal)) {
            Text(title, modifier = Modifier
                .padding(bottom = 2.dp),
                style = MaterialTheme.typography.bodySmall.copy(color = White.copy(alpha = .6f)))
            Text(subTitle,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W700
                ))
        }
    }
}

@Composable
fun UsedKwHComponent(powerUsed: String, cost: String){
    InaCard(modifier = Modifier.fillMaxWidth()
        .height(DimenTokens.Size.PowerUsageCard),
        cardElevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        )) {
        Box {
            Image(
                painter = painterResource(R.drawable.electricity_person),
                contentDescription = "Electricity background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter
                    .tint(Color.Gray.copy(alpha = .4f))
            )
            Row(
                modifier = Modifier.padding(DimenTokens.Padding.Normal)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val usedPower = buildAnnotatedString {

                    withStyle(
                        SpanStyle(
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = .6f)
                        )
                    ) {
                        append("Used\n\n")
                    }

                    withStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 50.sp,
                            fontWeight = FontWeight.W700
                        )
                    ) {
                        append(powerUsed)
                    }
                    withStyle(
                        SpanStyle(
                            fontSize = 13.sp
                        )
                    ) {
                        append("KWh")
                    }
                }

                val costString = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = .6f)
                        )
                    ) {
                        append("Costs ")
                    }

                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp,
                        )
                    ) {
                        append(cost)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight()
                ) {
                    Text(usedPower)
                    Text(costString)
                }
                Image(
                    painter = painterResource(R.drawable.image_light_bolt),
                    contentDescription = "Power bolt"
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewAllUnitUsageDetails(){
    AllUnitUsageDetails()
}