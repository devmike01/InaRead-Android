package dev.gbenga.inaread.ui.home

interface HomeStat {

    val reading: String
    val label: String
    val color: Long
}

class DefaultHomeStat(
    override val reading: String ="",
    override val label: String ="",
    override val color: Long = 0L
) : HomeStat{

}