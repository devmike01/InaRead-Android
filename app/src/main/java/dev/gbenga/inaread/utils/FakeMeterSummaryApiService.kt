package dev.gbenga.inaread.utils

import dev.gbenga.inaread.data.model.ApplianceRequest
import dev.gbenga.inaread.data.model.LastPowerUsage
import dev.gbenga.inaread.data.model.MeterResponse
import dev.gbenga.inaread.data.model.MonthChartResponse
import dev.gbenga.inaread.data.model.MonthlyMeterUsage
import dev.gbenga.inaread.domain.services.MeterSummaryApiService

class FakeMeterSummaryApiService : MeterSummaryApiService {

    override suspend fun fetchMeterSummary(userId: String): MeterResponse {
        return fakeMeterResponse
    }
}


val monthChartList = listOf(
    MonthChartResponse(month = "Jan", value = 120.5f, "2 May, 2024"),
    MonthChartResponse(month = "Feb", value = 98.2f, "2 May, 2024"),
    MonthChartResponse(month = "Mar", value = 135.0f, "2 May, 2024"),
    MonthChartResponse(month = "Apr", value = 150.8f, "2 May, 2024"),
    MonthChartResponse(month = "May", value = 170.3f, "2 May, 2024"),
    MonthChartResponse(month = "Jun", value = 160.0f, "2 May, 2024"),
    MonthChartResponse(month = "Jul", value = 180.6f, "2 May, 2024"),
    MonthChartResponse(month = "Aug", value = 175.4f, "2 May, 2024"),
    MonthChartResponse(month = "Sep", value = 155.9f, "2 May, 2024"),
    MonthChartResponse(month = "Oct", value = 140.2f, "2 May, 2024"),
    MonthChartResponse(month = "Nov", value = 130.7f, "2 May, 2024"),
    MonthChartResponse(month = "Dec", value = 165.1f, "2 May, 2024")
)

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
