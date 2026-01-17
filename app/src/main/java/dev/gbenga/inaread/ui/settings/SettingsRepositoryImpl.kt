package dev.gbenga.inaread.ui.settings

import dev.gbenga.inaread.data.model.ProfileResponse
import dev.gbenga.inaread.data.model.SettingsMenu
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.domain.settings.ProfileApiService
import dev.gbenga.inaread.domain.settings.SettingsRepository
import dev.gbenga.inaread.utils.UserNotFoundException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SettingsRepositoryImpl(
    val profileApiService: ProfileApiService,
    val userDataStore: UserDataStore,
    val ioContext: CoroutineContext) : SettingsRepository {

    override suspend fun getUserProfile(): ProfileResponse = useUserIdInIO {
        profileApiService.getProfile()
    }

    override fun getSettingsMenu(): List<SettingsMenu> {
        return listOf(
            SettingsMenu("Dark Mode", "true"),
            SettingsMenu("Logout", )
        )
    }

    private suspend fun <T> useUserIdInIO( block: suspend (String) -> T): T{
        val userId = userDataStore.getProfileId().firstOrNull() ?: throw UserNotFoundException()
        return withContext(ioContext){
            block(userId)
        }
    }
}