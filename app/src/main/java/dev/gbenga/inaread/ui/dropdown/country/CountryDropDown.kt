package dev.gbenga.inaread.ui.dropdown.country

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.InaSingleTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySuggestionTextField(value: String,
                               data: List<CountryData>,
                               onValueChanged: (String) -> Unit,
                               onCountrySelected: (CountryData) -> Unit) {

    var suggestionVisible by remember { mutableStateOf(false) }

    LaunchedEffect(data.isNotEmpty()) {
        suggestionVisible = data.isNotEmpty()
    }

    Column(modifier = Modifier.heightIn(max = 350.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(DimenTokens.Padding.Small)) {
        AnimatedVisibility(suggestionVisible) {
            Card(Modifier.fillMaxWidth().zIndex(10f)) {
                LazyColumn(Modifier
                    .heightIn(max = 250.dp)
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start) {
                    items(data.size){
                        TextButton(modifier = Modifier
                            .padding(DimenTokens.Padding.Small)
                            .fillMaxWidth(),
                            onClick = {
                                onCountrySelected(data[it])
                                suggestionVisible = false
                            }) {
                            Text(data[it].name)
                        }
                        if (it < data.size - 1){
                            HorizontalDivider(thickness = 1.dp,
                                modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }

        InaSingleTextField(value =value,
            placeholder =  "Input your country"){
            onValueChanged(it)
        }
    }
}
