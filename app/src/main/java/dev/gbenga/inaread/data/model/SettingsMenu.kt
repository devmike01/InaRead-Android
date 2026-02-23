package dev.gbenga.inaread.data.model

data class SettingsMenu(val name: String, val value: String? = null,
                        val key: SettingKeys=SettingKeys.PLACEHOLDER)

enum class SettingKeys{
    THEME_MODE, SIGN_OUT, PLACEHOLDER
}