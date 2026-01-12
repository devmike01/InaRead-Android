package dev.gbenga.inaread.ui.home

data class StatsChart(
    val lifeTimeReading: HomeStat = DefaultHomeStat(),
    val monthlyStat: HomeStat = DefaultHomeStat(),
    val costStat: HomeStat = DefaultHomeStat(),
    val chartData: List<Float> = emptyList()
)