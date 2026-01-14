package dev.gbenga.inaread.utils

import dev.gbenga.inaread.data.model.LastPowerUsage
import dev.gbenga.inaread.data.model.MeterResponse
import dev.gbenga.inaread.data.model.MonthlyMeterUsage
import dev.gbenga.inaread.domain.services.MeterSummaryApiService

class FakeMeterSummaryApiService : MeterSummaryApiService {

    override suspend fun fetchMeterSummary(userId: String): MeterResponse {
        return fakeMeterResponse
    }
}

val fakeMeterResponse = MeterResponse(
    monthlyMeterUsage = MonthlyMeterUsage(
        usages = listOf(
            12.5f, 14.2f, 13.8f, 15.1f,
            16.0f, 14.7f, 13.9f
        ),
        name = "January",
        kilowatts = 99.2,
        minorCost = 495_000L,          // ₦4,950.00
        nairaPerKWhMinor = 5_000L,     // ₦50.00 per kWh,
        month = 2
    ),
    usages = listOf(
        LastPowerUsage(
            kilowatts = 12.8,
            periodInDays = 6,
            minorCost = 68_200L,
            fromDayOfMonth = 1,
            toDayOfMonth = 7,
            month = 1
        ),
        LastPowerUsage(
            kilowatts = 18.4,
            periodInDays = 4,
            minorCost = 91_750L,
            fromDayOfMonth = 1,
            toDayOfMonth = 4,
            month = 1
        ),
        LastPowerUsage(
            kilowatts = 9.6,
            periodInDays = 7,
            minorCost = 54_300L,
            fromDayOfMonth = 5,
            toDayOfMonth = 11,
            month = 1
        ),
        LastPowerUsage(
            kilowatts = 21.1,
            periodInDays = 3,
            minorCost = 103_900L,
            fromDayOfMonth = 12,
            toDayOfMonth = 14,
            month = 1
        )
    )
)
