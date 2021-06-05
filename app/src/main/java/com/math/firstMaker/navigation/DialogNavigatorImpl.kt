package com.math.firstMaker.navigation

import androidx.fragment.app.FragmentManager
import com.math.firstMaker.views.makeCollection.dialog.ChapterPickerBottomDialog
import com.math.firstMaker.common.widget.bottomDialog.DatePickerBottomDialog
import com.math.firstMaker.views.makeCollection.dialog.SubjectPickerBottomDialog
import com.math.firstMaker.common.widget.bottomDialog.StudentAddBottomDialog
import com.math.firstMaker.model.BigChapter
import java.util.*


class DialogNavigatorImpl : DialogNavigator {
    override fun showDatePickerDialog(fm: FragmentManager, start : Calendar, end : Calendar) {
        DatePickerBottomDialog.show(fm, start, end )
    }

    override fun showSubjectPickDialog(fm: FragmentManager) {
        SubjectPickerBottomDialog.show(fm)
    }

    override fun showChapterPickerDialog(fm: FragmentManager, bigChapter : BigChapter ) {
        ChapterPickerBottomDialog.show(fm, bigChapter)
    }

    override fun showStudentAddDialog(fm: FragmentManager) {
        StudentAddBottomDialog.show(fm)
    }

}