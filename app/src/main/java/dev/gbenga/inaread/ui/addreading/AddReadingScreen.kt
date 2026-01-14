package dev.gbenga.inaread.ui.addreading

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gbenga.inaread.ui.home.UnitLaunchEffect

@Composable
fun AddReadingScreen(viewModel: AddReadingViewModel = hiltViewModel()) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    UnitLaunchEffect {
        viewModel.watchEvents()
    }

    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects media items or closes the
            // photo picker.
            viewModel.addImage(uri?.path)
        }

    pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Add New Meter Record",
            style = MaterialTheme.typography.headlineMedium)
    }
}
