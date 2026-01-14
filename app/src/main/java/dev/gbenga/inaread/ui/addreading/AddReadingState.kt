package dev.gbenga.inaread.ui.addreading

import dev.gbenga.inaread.data.model.UIMeterReadingInfo
import dev.gbenga.inaread.utils.InaReadUiState

data class AddReadingState(
    val meterImagePath: String? = null,
    val meterInfo: UIMeterReadingInfo = UIMeterReadingInfo(),): InaReadUiState