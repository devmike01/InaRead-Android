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
        name = "January Usage",
        kilowatts = 99.2,
        minorCost = 495_000L,          // ₦4,950.00
        nairaPerKWhMinor = 5_000L      // ₦50.00 per kWh
    ),
    lastUsage = LastPowerUsage(
        kilowatts = 15.3,
        periodInDays = 7,
        minorCost = 76_500L            // ₦765.00
    )
)
