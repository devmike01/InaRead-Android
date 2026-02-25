package dev.gbenga.inaread.ui.addreading

import dev.gbenga.inaread.data.model.ConsumptionRecordResponse
import dev.gbenga.inaread.data.model.UIMeterReadingInfo
import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle

data class AddReadingState(
    val meterImagePath: String? = null,
    val recordMeterSubmission: UiStateWithIdle<ConsumptionRecordResponse> = UiStateWithIdle.Idle,
    val meterInfo: UIMeterReadingInfo = UIMeterReadingInfo(),
    val enableRecordBtn: Boolean = false,
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