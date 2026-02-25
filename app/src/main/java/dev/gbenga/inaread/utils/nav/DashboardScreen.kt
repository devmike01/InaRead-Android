package dev.gbenga.inaread.utils.nav

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable


@Serializable
@Stable
sealed interface DashboardScreen : InaScreen {

    val key: String

    companion object{
        const val HOME_KEY = "DashboardScreen.HOME"
        const val SETTINGS_KEY = "DashboardScreen.SETTINGS_KEY"
        const val ADD_READING_KEY = "DashboardScreen.ADD_READING"
        const val ALL_TIME_USAGE = "DashboardScreen.ALL_TIME_USAGE"
    }

    @Serializable
    data class HomeScreen(override val key: String = HOME_KEY) : DashboardScreen

    @Serializable
    data class Settings(override val key: String= SETTINGS_KEY) : DashboardScreen

    @Serializable
    data class AllTimeUsage(override val key: String= ALL_TIME_USAGE) : DashboardScreen

    @Serializable
    data class AddReading(override val key: String= ADD_READING_KEY) : DashboardScreen
}