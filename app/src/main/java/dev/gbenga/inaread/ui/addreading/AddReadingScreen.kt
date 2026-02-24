package dev.gbenga.inaread.ui.addreading

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import dev.gbenga.inaread.R
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.color
import dev.gbenga.inaread.ui.home.HomeParentColumn
import dev.gbenga.inaread.ui.meterui.MeterFace
import dev.gbenga.inaread.utils.Scada

@Composable
fun AddReadingScreen(viewModel: AddReadingViewModel = hiltViewModel()) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val meterRecord = remember { mutableStateOf("") }

    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.addImage(uri)
        }

    AddReadingScreenContent(uiState = uiState, removeImageRequest = {
        viewModel.removeImage()
    },
onChangeReadingMode = {
    viewModel.toggleRecordMeterReading()
},
        manualRecordTextState = meterRecord) {
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

}


@Composable
fun AddReadingScreenContent(uiState: AddReadingState,
                            manualRecordTextState: MutableState<String>,
                            onChangeReadingMode: () -> Unit,
                            removeImageRequest: () -> Unit,
                            showImagePickerRequest: () -> Unit){

    val fromDate = remember { mutableStateOf("") }
    val toDate = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()){
        OutlinedButton (onClick = onChangeReadingMode,
            modifier = Modifier.align(
            Alignment.TopEnd
        ). padding(DimenTokens.Padding.Small)) {
            Icon(Icons.Default.SwapHoriz,
                contentDescription = "Toggle reading")
            Text("Toggle ${"Manual".takeIf { uiState.record == MeterReadingRecord.OCR }
                ?: "OCR"}",
                style = MaterialTheme
                    .typography
                    .titleSmall
                    .copy(fontWeight = FontWeight.W600))
        }

        HomeParentColumn(
            modifier = Modifier
                .padding(top = DimenTokens.Padding.Small)
                .align(Alignment.Center)
                .wrapContentHeight(),
            title = StringTokens.AddReadingImage.Title,
            subTitle = StringTokens.AddReadingImage.Subtitle
        ){
            AnimatedContent(uiState.record) { meterRecordToggle ->

            ConstraintLayout(modifier = Modifier
                .padding(top = DimenTokens.Padding.Small)
                .wrapContentHeight(),) {
                val (addImageBtn, readImgBtn) = createRefs()

              if (meterRecordToggle == MeterReadingRecord.OCR){
                        UploadImageButton(
                            uiState.meterImagePath,
                            modifier = Modifier.constrainAs(addImageBtn){
                                top.linkTo(parent.top)
                                //bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }, onClick = showImagePickerRequest,
                            onRemoveImageClick = removeImageRequest)

                        Button(
                            enabled = uiState.enableReadImage,
                            modifier = Modifier.constrainAs(readImgBtn){
                                top.linkTo(addImageBtn.bottom, margin = DimenTokens.Padding.Large)
                                //bottom.linkTo(parent.bottom, margin = DimenTokens.Padding.xXLarge)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                            onClick = {},
                        ) {
                            Text(StringTokens.AddReadingImage.ReadMeterImage,
                                style = MaterialTheme.typography.bodyLarge)
                        }
                    }else{
                        MeterFace(manualRecordTextState.value) {
                            manualRecordTextState.value = it
                        }
                    }
                }


            }
            Spacer(modifier = Modifier.padding(DimenTokens.Padding.Small))
            DatePickerDocked(modifier = Modifier, labelText = "From", fromDate)
            Spacer(modifier = Modifier.padding(DimenTokens.Padding.Small))
            DatePickerDocked(modifier = Modifier, labelText = "To",toDate)
            Spacer(modifier = Modifier.padding(DimenTokens.Padding.Large))
            Button(onClick = {

            }, Modifier.fillMaxWidth()
                .height(DimenTokens.Button.Normal),
                shape = RoundedCornerShape(DimenTokens.Radius.xSmall)
            ) {
                Text("Record reading")
            }
        }
    }
}


@Composable
fun UploadImageButton(imagePath: String?,
                      modifier: Modifier,
                      onRemoveImageClick: () -> Unit,
                      onClick: () -> Unit){

    val context = LocalContext.current
    AnimatedContent(imagePath,
        modifier = modifier.wrapContentSize()) { animatedPath ->
        animatedPath?.let {
            Scada.info("animatedPath: $animatedPath")
            Box(modifier = Modifier.size(
                DimenTokens.Image.Large)) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context)
                        .data(animatedPath)
                        .diskCachePolicy(CachePolicy.DISABLED)
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(
                        DimenTokens.Image.Large)
                        .clip(RoundedCornerShape(DimenTokens.Radius.large))
                )
                IconButton(onClick = onRemoveImageClick, modifier = Modifier.align(Alignment.TopEnd)
                    .size(DimenTokens.Icon.Medium)) {
                    Icon(Icons.Default.Cancel,
                        contentDescription = StringTokens.AddReadingImage.CancelImageDescription)
                }
            }
        } ?: Button(onClick = onClick,
            shape = RoundedCornerShape(DimenTokens.Radius.xLarge),
            modifier = Modifier.size(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = 0xFF37474F.color()
            ),
            contentPadding = PaddingValues(DimenTokens.Padding.Large),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 10.dp
            )
        ){
            Image(painter = painterResource(R.drawable.add_meter_reader_face_ic),
                contentDescription = StringTokens.AddMeterReading,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(DimenTokens.Icon.Large))

        }
    }
}





@Composable
@Preview
fun PreviewAddReadingScreenContent(){
    AddReadingScreenContent(previewAddReadingState,
        removeImageRequest = {},
        onChangeReadingMode = {},
        manualRecordTextState = remember { mutableStateOf("") }){
    }
}


@Composable
@Preview
fun PreviewAddReadingScreenContentWithImage(){
    AddReadingScreenContent(previewAddReadingState.copy(meterImagePath = "file://android_asset/preview_meter_reading.png"),
        removeImageRequest = {},
        onChangeReadingMode = {},
        manualRecordTextState = remember { mutableStateOf("") }){

    }
}