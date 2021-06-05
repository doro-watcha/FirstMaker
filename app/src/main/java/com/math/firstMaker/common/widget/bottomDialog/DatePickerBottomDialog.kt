package com.math.firstMaker.common.widget.bottomDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.AutoClearedValue
import com.math.firstMaker.databinding.BottomDialogDateBinding
import com.math.firstMaker.debugE
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


/**
 * created By DORO 2020-03-22
 */
class DatePickerBottomDialog : BottomSheetDialogFragment() {

    companion object {
        fun show(fm: FragmentManager, start : Calendar, end : Calendar) {
            val dialog = DatePickerBottomDialog()

            dialog.start = start
            dialog.end = end
            dialog.show(fm, dialog.tag)
        }
    }


    private val TAG = "DatePickerBottomDialog"
    override fun getTheme(): Int = R.style.BottomSheetDialog

    private lateinit var start : Calendar
    private lateinit var end : Calendar


    /**
     * Binding Instance
     */
    private var mBinding: BottomDialogDateBinding by AutoClearedValue()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)

        bottomSheetDialog.behavior.peekHeight = 1800


        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return BottomDialogDateBinding.inflate(inflater, container, false)
            .also { mBinding = it }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mBinding.apply {

            lifecycleOwner = viewLifecycleOwner

            datePicker.apply {
                setSelectedDateRange(start ,end)

                setCalendarListener(object : CalendarListener {
                    override fun onFirstDateSelected(startDate: Calendar?) {
                    }

                    override fun onDateRangeSelected(startDate: Calendar?, endDate: Calendar?) {
                        debugE(TAG, "Start Date: " + startDate?.time.toString() + " End date: " + endDate?.time.toString())
                    }

                })
            }

            btnPickDate.setOnDebounceClickListener {
                val start : Calendar? = datePicker.startDate
                val end : Calendar? = datePicker.endDate

                debugE(TAG, start?.time)
                debugE(TAG, end?.time)
                Broadcast.datePickBroadcast.onNext(Pair(start,end))

                dismiss()
            }

            button1.setOnDebounceClickListener{
                setupWeeklyCalendar(1)
            }
            button2.setOnDebounceClickListener {
                setupCalendar(1)
            }
            button3.setOnDebounceClickListener {
                setupCalendar(2)
            }
            button4.setOnDebounceClickListener {
                setupCalendar(3)
            }

        }
    }
    private fun setupWeeklyCalendar ( duration : Int ) {

        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        start.add(Calendar.WEEK_OF_MONTH, -duration)

        mBinding.datePicker.setSelectedDateRange(start,end)
    }

    private fun setupCalendar ( duration : Int ) {
        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        start.add(Calendar.MONTH,-duration)

        mBinding.datePicker.setSelectedDateRange(start,end)

    }


    private fun DateRangeCalendarView.setCalendarListener(calendarListener: CalendarListener) {
        debugE(TAG, startDate)
        calendarListener.onFirstDateSelected(mBinding.datePicker.startDate)
        calendarListener.onDateRangeSelected(mBinding.datePicker.startDate,mBinding.datePicker.endDate)

    }



}
