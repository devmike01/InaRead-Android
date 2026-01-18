package dev.gbenga.inaread.ui.metric

@Deprecated("Marked for removal because of internal loop in viewmodel")

sealed interface MetricEvent {
    object PopulateScreen: MetricEvent
    object LoadAppliances: MetricEvent
}