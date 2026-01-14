package dev.gbenga.inaread.ui.addreading

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.inaread.R
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.HorizontalCenter
import dev.gbenga.inaread.ui.customs.color
import dev.gbenga.inaread.ui.home.HomeParentColumn
import dev.gbenga.inaread.ui.home.UnitLaunchEffect

@Composable
fun AddReadingScreen(viewModel: AddReadingViewModel = hiltViewModel()) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    UnitLaunchEffect {
        viewModel.watchEvents()
    }

    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.addImage(uri?.path)
        }

//    UnitLaunchEffect {
//        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }


    HomeParentColumn("Add Meter Image for Analysis",
        subTitle = "Upload a meter photo to extract and analyze the meter readings"){
        HorizontalCenter (modifier = Modifier
            .padding(top = DimenTokens.Padding.xLarge)
            .fillMaxSize(),) {
            UploadImageButton(uiState.meterImagePath) {
                pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }
}


@Composable
fun UploadImageButton(imagePath: String?, onClick: () -> Unit){

    imagePath?.let {
//        AsyncImage(
//            model = imagePath,
//            contentDescription = null,
//        )
    } ?: Button(onClick = onClick,
        shape = RoundedCornerShape(DimenTokens.Radius.xLarge),
        modifier = Modifier.width(170.dp)
            .height(180.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = 0xFF37474F.color()
        ),
        contentPadding = PaddingValues(DimenTokens.Padding.large),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 10.dp
        )
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(R.drawable.add_meter_reader_face_ic),
                contentDescription = StringTokens.AddMeterReading,
                modifier = Modifier.size(DimenTokens.Size.icon))
            Text("Add Image",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W700,
                    color = Color.White))
        }
    }
}