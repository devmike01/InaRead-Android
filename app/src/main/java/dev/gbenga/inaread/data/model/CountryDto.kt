package dev.gbenga.inaread.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(val name: String, val isoCode: String)