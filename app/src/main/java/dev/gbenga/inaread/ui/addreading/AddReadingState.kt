package dev.gbenga.inaread.ui.addreading

import dev.gbenga.inaread.data.model.UIMeterReadingInfo
import dev.gbenga.inaread.utils.InaReadUiState

data class AddReadingState(
    val meterImagePath: String? = null,
    val meterInfo: UIMeterReadingInfo = UIMeterReadingInfo(),
    val enableReadImage: Boolean = false,
    val record: MeterReadingRecord = MeterReadingRecord.MANUAL,
    ): InaReadUiState


enum class MeterReadingRecord{
    MANUAL, OCR
}

val previewAddReadingState = AddReadingState(
    meterImagePath = null,
    meterInfo = UIMeterReadingInfo(
        "500.33kWh",
        from = "2 January, 2026",
        to = "13 January, 2026",
    ),
    record = MeterReadingRecord.MANUAL
)