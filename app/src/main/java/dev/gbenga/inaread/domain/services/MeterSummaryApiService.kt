package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.MeterResponse
import retrofit2.http.GET

interface MeterSummaryApiService {

    @GET("/")
    suspend fun fetchMeterSummary(userId: String): MeterResponse

}