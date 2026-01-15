package dev.gbenga.inaread.data.model

data class ApplianceResponse(
    val name: String,
    val rating: String)


data class ApplianceRequest(
    val name: String,
    val rating: String,
)

data class Appliance( val name: String,
                      val rating: String,)

fun ApplianceResponse.toAppliance(): Appliance{
    return Appliance(
        name = name, rating = rating
    )
}