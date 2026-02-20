package dev.gbenga.inaread.data.model

import java.math.BigDecimal



data class Appliance(  val name: String,
                       val rating: BigDecimal,
                       val applianceTypeId: Long,
                       val ratingUnit: String,
    val createdOn: String)



data class ApplianceResponseData(
    val name: String,
    val rating: BigDecimal,
    val applianceTypeId: Long,
    val ratingUnit: String
)


data class ApplianceRequest(
    val rating: BigDecimal,
    val customerId: String,
    val applianceTypeId: Long,
    val ratingUnit: String
)


data class AppliancesRequest(
    val appliances: List<ApplianceRequest>
)


fun ApplianceResponseData.toAppliance(): Appliance{
    return Appliance(
        name = name,
        rating = rating,
        applianceTypeId = applianceTypeId,
        ratingUnit = ratingUnit,
        createdOn = "n/a"
    )
}

data class ApplianceResponse(
    val data: List<Appliance>,
    val error: String?
)