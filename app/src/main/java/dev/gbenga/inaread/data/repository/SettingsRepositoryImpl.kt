package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.model.ProfileResponse
import dev.gbenga.inaread.data.model.SettingKeys
import dev.gbenga.inaread.data.model.SettingsMenu
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.domain.services.ProfileApiService
import dev.gbenga.inaread.domain.repository.SettingsRepository
import dev.gbenga.inaread.utils.UserNotFoundException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    val profileApiService: ProfileApiService,
    val userDataStore: UserDataStore,
    val ioContext: CoroutineDispatcher
) : SettingsRepository {

    override suspend fun getUserProfile(): ProfileResponse = useUserIdInIO {
        withContext(ioContext){
            profileApiService.getProfile()
        }
    }

    override fun getSettingsMenu(): List<SettingsMenu> {
        return listOf(
            SettingsMenu("Dark Mode", "true", SettingKeys.THEME_MODE),
            SettingsMenu("Logout", key = SettingKeys.SIGN_OUT)
        )
    }

    private suspend fun <T> useUserIdInIO( block: suspend (String) -> T): T{
        val userId = userDataStore.getProfileId().firstOrNull() ?: throw UserNotFoundException()
        return withContext(ioContext) {
            block(userId)
        }
    }
}