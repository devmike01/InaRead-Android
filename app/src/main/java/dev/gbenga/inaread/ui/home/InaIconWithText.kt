package dev.gbenga.inaread.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector


@Immutable
data class ResIdInaTextIcon(
    override val value: String,
    override val label: String,
    override val color: Long,
    @DrawableRes val icon: Int? =null,
) : InaTextIcon

@Immutable
data class VectorInaTextIcon(
    val icon: ImageVector? =null,
    override val value: String,
    override val label: String,
    override val color: Long,
    val key: Any? = null,
) : InaTextIcon

data class InaBottomNavItemData(val icon: ImageVector? =null, val label: String,)


typealias MeterUsageSummary = List<InaTextIcon>
