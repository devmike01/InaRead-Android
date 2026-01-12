package dev.gbenga.inaread.log

import android.util.Log
import dev.gbenga.inaread.utils.Scada
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ScadaTest {

    companion object{
        const val LogMessage = "Test log"
    }

    @Before
    fun init(){
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.w(any(String::class), any(String::class)) } returns 0
    }

    @Test
    fun verifyScadaLogs(){
        Scada.info(LogMessage)
        verify { Log.i("testLogInfo", LogMessage) }
    }
}