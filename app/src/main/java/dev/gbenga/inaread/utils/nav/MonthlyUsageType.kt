package dev.gbenga.inaread.utils.nav

import android.util.Log
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.google.gson.Gson
import dev.gbenga.inaread.data.model.MonthlyUsage
import kotlinx.serialization.json.Json

val MonthlyUsageType = object : NavType<MonthlyUsage>(isNullableAllowed = false) {

    override fun put(
        bundle: SavedState,
        key: String,
        value: MonthlyUsage
    ) {
        bundle.putString(key, Json.encodeToString(value = value))
    }

    override fun get(
        bundle: SavedState,
        key: String
    ): MonthlyUsage? {
        return bundle.getString(key)
            ?.let { Json.decodeFromString(it)}
    }

    override fun parseValue(value: String): MonthlyUsage {
        Log.i("MonthlyUsageType", "value: $value")
        return Json.decodeFromString(value)
    }

    override fun serializeAsValue(value: MonthlyUsage): String {
        return Json.encodeToString(value)
    }
}