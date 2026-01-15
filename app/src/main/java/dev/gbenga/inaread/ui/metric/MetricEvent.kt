package dev.gbenga.inaread.ui.metric

sealed interface MetricEvent {
    object PopulateScreen: MetricEvent
    object LoadAppliances: MetricEvent
}