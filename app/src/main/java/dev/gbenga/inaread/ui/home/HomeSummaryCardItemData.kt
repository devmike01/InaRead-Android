package dev.gbenga.inaread.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp


data class ResIdIconItem(
    @DrawableRes val icon: Int,
    override val reading: String,
    override val label: String,
    override val color: Long,
) : HomeStat

data class VectorItem(
    val icon: ImageVector,
    override val reading: String,
    override val label: String,
    override val color: Long,
) : HomeStat


typealias HomeSummaryCardItems = List<HomeStat>