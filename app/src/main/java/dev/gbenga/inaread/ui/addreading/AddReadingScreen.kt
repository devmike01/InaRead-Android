package dev.gbenga.inaread.ui.addreading

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import dev.gbenga.inaread.ui.customs.HorizontalCenter
import dev.gbenga.inaread.ui.customs.color
import dev.gbenga.inaread.ui.home.HomeParentColumn
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.utils.Scada
import dev.gbenga.inaread.utils.UiState

@Composable
fun AddReadingScreen(viewModel: AddReadingViewModel = hiltViewModel()) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    UnitLaunchEffect {
        viewModel.watchEvents()
    }

    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.addImage(uri)
        }

    AddReadingScreenContent(uiState = uiState, removeImageRequest = {
        viewModel.removeImage()
    }) {
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

}

@Composable
fun AddReadingScreenContent(uiState: AddReadingState,
                            removeImageRequest: () -> Unit,
                            showImagePickerRequest: () -> Unit){

    Box(modifier = Modifier.fillMaxSize()){
        HomeParentColumn(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentHeight()
                .fillMaxWidth(),
            title = StringTokens.AddReadingImage.Title,
            subTitle = StringTokens.AddReadingImage.Subtitle
        ){
            ConstraintLayout(modifier = Modifier
                .padding(top = DimenTokens.Padding.large)
                .wrapContentHeight(),) {
                val (addImageBtn, readImgBtn) = createRefs()

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
                        top.linkTo(addImageBtn.bottom, margin = DimenTokens.Padding.large)
                        //bottom.linkTo(parent.bottom, margin = DimenTokens.Padding.xXLarge)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    onClick = {},
                ) {
                    Text(StringTokens.AddReadingImage.ReadMeterImage,
                        style = MaterialTheme.typography.bodyLarge)
                }
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
            contentPadding = PaddingValues(DimenTokens.Padding.large),
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
    AddReadingScreenContent(previewAddReadingState, removeImageRequest = {}){
    }
}


@Composable
@Preview
fun PreviewAddReadingScreenContentWithImage(){
    AddReadingScreenContent(previewAddReadingState.copy(meterImagePath = "file://android_asset/preview_meter_reading.png"), removeImageRequest = {}){

    }
}