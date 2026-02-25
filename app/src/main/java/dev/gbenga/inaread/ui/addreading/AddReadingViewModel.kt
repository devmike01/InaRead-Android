package dev.gbenga.inaread.ui.addreading

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.datastore.Messenger
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.PowerUsageRequest
import dev.gbenga.inaread.domain.providers.ImagePickerProvider
import dev.gbenga.inaread.domain.usecase.MeterUsageRecordingUseCase
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.Scada
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.date.InaDateFormatter
import dev.gbenga.inaread.utils.nav.DashboardScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddReadingViewModel @Inject constructor(
    private val meterUsageRecordingUseCase: MeterUsageRecordingUseCase,
    private val inaDateFormatter: InaDateFormatter,
    private val imagePickerProvider: ImagePickerProvider,
    private val messenger: Messenger)
    : InaReadViewModelV2<AddReadingState>(AddReadingState()) {


    fun addImage(imageUri: Uri?){
        viewModelScope.launch {
            imageUri?.let { imageUri->
                val selectedImagePath = imagePickerProvider
                    .getAbsolutePathFor(imageUri)
                Scada.info("AddReadingEvent: $selectedImagePath")
                setState { it.copy(
                    meterImagePath = selectedImagePath)
                }
            }
        }

    }

    fun toggleRecordMeterReading(){
        setState { state -> state.copy(
            record = MeterReadingRecord.MANUAL
                .takeIf { state.record ==  MeterReadingRecord.OCR}
                ?: MeterReadingRecord.OCR
        ) }
    }


    fun recordReading(fromDate: Long, toDate: Long, reading: String){
        setState { it.copy(recordMeterSubmission = UiStateWithIdle.Loading) }
        viewModelScope.launch {
            when(val repo = meterUsageRecordingUseCase(PowerUsageRequest(
                fromDate = inaDateFormatter.yyyyMMDD(Date.from(Instant
                    .ofEpochMilli(fromDate))),
                toDate = inaDateFormatter.yyyyMMDD(Date.from(
                    Instant.ofEpochMilli(toDate))),
                readingText = reading
            ))){
                is RepoResult.Success -> {
                    setState {
                        it.copy(recordMeterSubmission = UiStateWithIdle.Success(repo.data))
                    }
                    navigate(NavigationEvent.NavigateTaskTop(DashboardScreen.HomeScreen()))
                    showMessage("Power usage successfully recorded")
                }
                is RepoResult.Error -> {
                    showMessage(repo.message)
                    setState {
                        it.copy(recordMeterSubmission
                        = UiStateWithIdle.Error(repo.message))
                    }
                }
            }
        }
    }


    fun toggleSumitButton(reading: String, fromDateInMillis: Long, toDateInMillis: Long){
        //Log.d("toggleSumit", "$reading : $fromDateInMillis : $toDateInMillis")
        val isValidReading = try {
            BigDecimal(reading).longValueExact() > 0
        }catch (e: Exception){
            false
        }
        val areDateValid = fromDateInMillis > 0 && toDateInMillis > 0 && (fromDateInMillis < toDateInMillis)
        setState { it.copy(enableRecordBtn = isValidReading && areDateValid) }
    }

    private fun toggleImage(enabled: Boolean){
        setState { it.copy(
            enableRecordBtn = enabled && it.record == MeterReadingRecord.OCR)
        }
    }

    fun showMessage(message: String){
        viewModelScope.launch {
            messenger.sendMessage(message)
        }
    }

    fun removeImage(){
        setState { it.copy(meterImagePath = null) }
        toggleImage(false)
    }

}