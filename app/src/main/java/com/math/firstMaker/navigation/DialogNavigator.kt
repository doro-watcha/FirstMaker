package com.math.firstMaker.navigation

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.math.firstMaker.common.Once
import com.math.firstMaker.model.BigChapter
import java.util.*

interface DialogNavigator {

    fun showDatePickerDialog (fm: FragmentManager, start : Calendar, end : Calendar)

    fun showSubjectPickDialog ( fm : FragmentManager)

    fun showChapterPickerDialog ( fm : FragmentManager, bigChapter : BigChapter )

    fun showStudentAddDialog ( fm : FragmentManager)
}