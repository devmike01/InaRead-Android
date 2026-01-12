package dev.gbenga.inaread.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector


data class ResIdIconItem(
    override val reading: String,
    override val label: String,
    override val color: Long,
    @DrawableRes val icon: Int? =null,
) : HomeStat

data class VectorItem(
    val icon: ImageVector? =null,
    override val reading: String,
    override val label: String,
    override val color: Long,
) : HomeStat


typealias MeterUsageSummary = List<HomeStat>