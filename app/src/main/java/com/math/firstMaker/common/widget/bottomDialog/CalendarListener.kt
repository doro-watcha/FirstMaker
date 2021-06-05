package com.math.firstMaker.common.widget.bottomDialog

import java.util.*

interface CalendarListener {
    fun onFirstDateSelected(startDate: Calendar?)
    fun onDateRangeSelected(startDate: Calendar?, endDate: Calendar?)
}