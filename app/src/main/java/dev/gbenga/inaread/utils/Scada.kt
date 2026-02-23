package dev.gbenga.inaread.utils

import android.util.Log
import dev.gbenga.inaread.BuildConfig

object Scada {

    private fun getNameOfCaller(): String {
        return Thread.currentThread().getStackTrace().also {
        }[3].methodName
    }

    fun info(msg: String) = executeInDebugEnv{
        executeInDebugEnv{
            Log.i(getNameOfCaller(), msg)
        }
    }

    fun warning(msg: String) = executeInDebugEnv{
        Log.w("", msg)
    }

    fun error(msg: String) = executeInDebugEnv{
        Log.w("", msg)
    }

}

inline fun <T> executeInDebugEnv(block: () -> T): T?{
    return if (BuildConfig.DEBUG){
        return block()
    } else null
}