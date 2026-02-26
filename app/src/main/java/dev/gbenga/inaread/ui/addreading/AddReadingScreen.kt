package dev.gbenga.inaread.ui.addreading

import android.util.Log
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import dev.gbenga.inaread.R
import dev.gbenga.inaread.data.model.PowerUsageRequest
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.color
import dev.gbenga.inaread.ui.dialogs.LoadingDialog
import dev.gbenga.inaread.ui.home.HomeParentColumn
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.ui.meterui.MeterFace
import dev.gbenga.inaread.ui.theme.Dark
import dev.gbenga.inaread.ui.theme.White
import dev.gbenga.inaread.utils.Scada
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.rememberNavigationDelegate
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset

@Composable
fun AddReadingScreen(viewModel: AddReadingViewModel,
                     parentNavigator: NavController) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val meterRecord = rememberSaveable { mutableStateOf("") }
    var fromDate by rememberSaveable { mutableStateOf(System.currentTimeMillis()) }
    var toDate by rememberSaveable { mutableStateOf(System.currentTimeMillis()) }
    val isLoading by remember { derivedStateOf { uiState.recordMeterSubmission is UiStateWithIdle.Loading } }
    val navigationDelegate = rememberNavigationDelegate(parentNavigator)

    LaunchedEffect(meterRecord.value, fromDate, toDate) {
        viewModel.toggleSumitButton(meterRecord.value, fromDate, toDate)
    }

    UnitLaunchEffect {
        viewModel.navigator.collect {
            navigationDelegate.handleEvents(it)
        }
    }

    LoadingDialog(isLoading)

    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.addImage(uri)
        }

        AddReadingScreenContent(
            uiState = uiState, removeImageRequest = {
                viewModel.removeImage()
            },
            onChangeReadingMode = {
                viewModel.toggleRecordMeterReading()
            },
            manualRecordTextState = meterRecord.value,
            toDate = toDate,
            fromDate = fromDate,
            onFromDateChanged = {
                fromDate = it
            },
            onToDateChanged = {
                toDate = it
            },

            onSubmitClick = {
                viewModel.recordReading(
                    fromDate, toDate, meterRecord.value
                )
            },
            onInputUsage = {
                meterRecord.value = it
                //  viewModel.removeImage()
            }) {
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    }

}


@Composable
fun AddReadingScreenContent(
    uiState: AddReadingState,
    manualRecordTextState: String,
    fromDate: Long,
    toDate: Long,
    onSubmitClick: () -> Unit,
    onFromDateChanged: (Long) -> Unit,
    onToDateChanged: (Long) -> Unit,
    onInputUsage: (String) -> Unit,
    onChangeReadingMode: () -> Unit,
    removeImageRequest: () -> Unit,
    showImagePickerRequest: () -> Unit
) {


    Box(modifier = Modifier.fillMaxSize()) {
        OutlinedButton(
            onClick = onChangeReadingMode,
            modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .padding(
                    vertical = DimenTokens.Padding.Small,
                    horizontal = DimenTokens.Padding.XSmall
                )
        ) {
            Icon(
                Icons.Default.SwapHoriz,
                contentDescription = "Toggle reading"
            )
            Text(
                "Toggle ${
                    "Manual".takeIf { uiState.record == MeterReadingRecord.OCR }
                        ?: "OCR"
                }",
                style = MaterialTheme
                    .typography
                    .titleSmall
                    .copy(fontWeight = FontWeight.W600))
        }

        HomeParentColumn(
            modifier = Modifier
                 .padding(top = DimenTokens.Padding.XLarge)
                .align(Alignment.TopCenter)
                .wrapContentHeight(),
            title = StringTokens.AddReadingImage.Title,
            subTitle = StringTokens.AddReadingImage.Subtitle
        ) {
            AnimatedContent(uiState.record) { meterRecordToggle ->

                ConstraintLayout(
                    modifier = Modifier
                        .padding(top = DimenTokens.Padding.Small)
                        .wrapContentHeight(),
                ) {
                    val (addImageBtn) = createRefs()

                    if (meterRecordToggle == MeterReadingRecord.OCR) {
                        UploadImageButton(
                            uiState.meterImagePath,
                            modifier = Modifier.constrainAs(addImageBtn) {
                                top.linkTo(parent.top)
                                //bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }, onClick = showImagePickerRequest,
                            onRemoveImageClick = removeImageRequest
                        )


                    } else {
                        MeterFace(manualRecordTextState) {
                            onInputUsage(it)
                        }
                    }
                }


            }
            Spacer(modifier = Modifier.padding(DimenTokens.Padding.Small))
            DatePickerFieldToModal(
                modifier = Modifier, labelText = "From",
                fromDate
            ) {
                //fromDate.value = it
                onFromDateChanged(it)
            }
            Spacer(modifier = Modifier.padding(DimenTokens.Padding.Small))
            DatePickerFieldToModal(
                modifier = Modifier, labelText = "To",
                toDate
            ) {
                //toDate.value = it
                onToDateChanged(it)
            }
            Spacer(modifier = Modifier.padding(DimenTokens.Padding.Small))
            Button(
                enabled = uiState.enableRecordBtn,
                onClick = onSubmitClick, modifier = Modifier
                    .fillMaxWidth()
                    .height(DimenTokens.Button.Normal),
                shape = RoundedCornerShape(DimenTokens.Radius.xSmall)
            ) {
                Text("Record reading")
            }
        }
    }
}


@Composable
fun UploadImageButton(
    imagePath: String?,
    modifier: Modifier,
    onRemoveImageClick: () -> Unit,
    onClick: () -> Unit
) {

    val context = LocalContext.current
    AnimatedContent(
        imagePath,
        modifier = modifier.wrapContentSize()
    ) { animatedPath ->
        animatedPath?.let {
            Scada.info("animatedPath: $animatedPath")
            Box(
                modifier = Modifier.size(
                    DimenTokens.Image.Large
                )
            ) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context)
                        .data(animatedPath)
                        .diskCachePolicy(CachePolicy.DISABLED)
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            DimenTokens.Image.Large
                        )
                        .clip(RoundedCornerShape(DimenTokens.Radius.large))
                )
                IconButton(
                    onClick = onRemoveImageClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(DimenTokens.Icon.Medium)
                ) {
                    Icon(
                        Icons.Default.Cancel,
                        contentDescription = StringTokens.AddReadingImage.CancelImageDescription
                    )
                }
            }
        } ?: Button(
            onClick = onClick,
            shape = RoundedCornerShape(DimenTokens.Radius.xLarge),
            modifier = Modifier.size(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = 0xFF37474F.color()
            ),
            contentPadding = PaddingValues(DimenTokens.Padding.Small),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 10.dp
            )
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = StringTokens.AddMeterReading,
                tint = White,
                //contentScale = ContentScale.Fit,
                modifier = Modifier.size(DimenTokens.Icon.Large)
            )

        }
    }
}


@Composable
@Preview
fun PreviewAddReadingScreenContent() {
    AddReadingScreenContent(
        previewAddReadingState,
        removeImageRequest = {},
        onChangeReadingMode = {},
        onInputUsage = {},
        fromDate = 0L,
        toDate = 0L,
        onToDateChanged = {},
        onSubmitClick = {},
        onFromDateChanged = {},
        manualRecordTextState = ""
    ) {
    }
}


@Composable
@Preview
fun PreviewAddReadingScreenContentWithImage() {
    AddReadingScreenContent(
        previewAddReadingState.copy(meterImagePath = "file://android_asset/preview_meter_reading.png"),
        removeImageRequest = {},
        onChangeReadingMode = {},
        onInputUsage = {},
        fromDate = 0L,
        toDate = 0L,
        onToDateChanged = {},
        onFromDateChanged = {},
        onSubmitClick = {},
        manualRecordTextState = ""
    ) {

    }
}