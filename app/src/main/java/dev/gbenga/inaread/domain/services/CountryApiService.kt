package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.CountryResponse
import dev.gbenga.inaread.data.network.EndPoints
import retrofit2.Response
import retrofit2.http.GET

interface CountryApiService {

    @GET(EndPoints.ALL_COUNTRIES)
    suspend fun getCountries(): Response<ApiResult<List<CountryResponse>>>
}