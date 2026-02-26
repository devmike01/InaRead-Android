package dev.gbenga.inaread.data.repository

import dev.gbenga.inaread.data.db.entities.UserEntity
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.SettingKeys
import dev.gbenga.inaread.data.model.SettingsMenu
import dev.gbenga.inaread.domain.repository.SettingsRepository
import dev.gbenga.inaread.domain.services.ProfileApiService
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.utils.UserProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    val profileApiService: ProfileApiService,
    val userProvider: UserProvider,
    val ioContext: CoroutineDispatcher
) : SettingsRepository, NetworkRepository() {

    override suspend fun getUserProfile(): RepoResult<UserEntity>{
        return try {
            withContext(ioContext){
                val currentUser = userProvider.getCurrentUser()
                RepoResult.Success(currentUser)
            }
        }catch (e: Exception){
            RepoResult.Error(e.message ?: StringTokens.UnknownErrorOccured)
        }
    }

    override fun getSettingsMenu(): List<SettingsMenu> {
        return listOf(
            SettingsMenu("Dark Mode", "true", SettingKeys.THEME_MODE),
            SettingsMenu("Logout", key = SettingKeys.SIGN_OUT)
        )
    }

}