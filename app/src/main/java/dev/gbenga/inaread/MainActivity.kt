package dev.gbenga.inaread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import dev.gbenga.inaread.ui.addreading.ImagePickerProvider
import dev.gbenga.inaread.ui.theme.InaReadTheme
import dev.gbenga.inaread.utils.nav.InaReadApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
           setContent {
               InaReadApp()
        }
    }
}
