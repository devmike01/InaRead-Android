package dev.gbenga.inaread.data.model



data class DateAndMonth(val dayOfMonth: Int,
                        val ymdDate: String,
                        val monthValue: Int,
                        val month: String,
                        val timeInMillis: Long)