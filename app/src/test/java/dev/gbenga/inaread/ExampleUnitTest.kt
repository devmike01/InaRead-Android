package dev.gbenga.inaread

import dev.gbenga.inaread.ui.dropdown.country.CountryData
import dev.gbenga.inaread.ui.signup.binarySearch
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("Searched: ${listOf("Nigeria", "Togo", "Zambia").map {
            CountryData(name = it, iso = it.substring(0, 2))
        }.binarySearch("To")}")
    }
}