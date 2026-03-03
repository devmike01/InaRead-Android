package dev.gbenga.inaread.data.model

data class ElectricityBandResponse(
    val id: Int,
    val name: String,
    val hoursDaily: String,
    val pricePerKwh: Double,
    val currency: String,
    val meterType: String
)