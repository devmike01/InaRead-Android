package dev.gbenga.inaread.data.model

import dev.gbenga.inaread.ui.home.DefaultInaTextIcon
import dev.gbenga.inaread.ui.home.InaTextIcon

private val defaultIcon = DefaultInaTextIcon()
data class MeterMonthlyStat(
    val lifeTimeReading: InaTextIcon = defaultIcon,
    val monthlyStat: InaTextIcon = defaultIcon,
    val costStat: InaTextIcon = defaultIcon,
    val chartData: List<Float> = emptyList()
)