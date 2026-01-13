package dev.gbenga.inaread.ui.home

interface InaIcon {

    val value: String
    val label: String
    val color: Long
}

class DefaultInaIcon(
    override val value: String ="",
    override val label: String ="",
    override val color: Long = 0L
) : InaIcon{

}