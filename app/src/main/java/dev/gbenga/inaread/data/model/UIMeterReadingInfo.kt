package dev.gbenga.inaread.data.model

data class UIMeterReadingInfo(
    val usage: String = "",
    val from: String = "",
    val to: String = "",
    val meterScreenImage: String? = null,
    val notes: String? = null
)