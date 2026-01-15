package dev.gbenga.inaread.ui.addreading

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


@Deprecated("Use the one directly in the UI")
class ImagePickerProvider(private val context: Context,) {

    private val _pickedImage = Channel<Result<Uri>>()
    val pickedImage = _pickedImage.receiveAsFlow()

    fun Context.asActivity(): ComponentActivity{
        return this as? ComponentActivity ?: throw IllegalArgumentException("$this is not a context of an activity")
    }

    // Registers a photo picker activity launcher in single-select mode.
    val pickMedia = context.asActivity()
        .registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                _pickedImage.trySend(Result.success(it))
            } ?: _pickedImage.trySend(Result
                .failure(NullPointerException("No image was selected")))
        }

    fun pickImage(){
        // Launch the photo picker and let the user choose only images.
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}