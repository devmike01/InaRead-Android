package dev.gbenga.inaread.ui.home

interface InaTextIcon {

    val value: String
    val label: String
    val color: Long
}

class DefaultInaTextIcon(
    override val value: String ="",
    override val label: String ="",
    override val color: Long = 0L
) : InaTextIcon{

}