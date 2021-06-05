package com.math.firstMaker.views.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentSettingBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.views.auth.LoginActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class SettingFragment : Fragment() {


    private val TAG = SettingFragment::class.java.simpleName

    private val dateParserUtil : DateParserUtil by inject()
    /**
     * Binding Instance
     */
    private var _mBinding: FragmentSettingBinding? = null
    private val mBinding: FragmentSettingBinding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */
    private val mViewModel : SettingViewModel by viewModel()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        FragmentSettingBinding.inflate(inflater, container, false).also { _mBinding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()

        setupBarChart()
        setupLineChart()
        setupRecyclerView()
        setupBroadcast()
    }

    private fun setupBarChart () {
        mBinding.mBarChart.apply {

            adapter = WeeklyProblemAdapter(context).apply {
            }
        }
    }

    private fun setupLineChart () {

        mBinding.mLineChart.apply {

            setPinchZoom(false)
            setScaleEnabled(false)
            description.isEnabled = false
            axisLeft.setDrawLabels(false)
            axisRight.setDrawLabels(false)
            highlightValue(null)




        }
    }
    private fun setupRecyclerView() {
        mBinding.vulnerableRecyclerview.apply {

            adapter = VulnerableAdapter()
        }

        mBinding.mMyClassRecyclerView.apply {

            adapter = MyClassAdapter()
        }
    }


    private fun observeViewModel () {

        mViewModel.apply {

            weeklyCorrectRate.observe(viewLifecycleOwner){

                debugE(TAG, "Weekly Correct Rate!")

                val today = Calendar.getInstance()
                today.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)


                val xEntries = ArrayList<String>()
                val yEntries = ArrayList<Entry>()
                for ( i in it.indices) {
                    val todayDate = dateParserUtil.parseToD(today)
                    xEntries.add(todayDate)
                    today.add(Calendar.DAY_OF_WEEK, 1)
                    yEntries.add(Entry(i.toFloat(), it[i]))
                }
                val lineDataSet = LineDataSet(yEntries, "이번 주 정답률")
                lineDataSet.color = resources.getColor(R.color.default_main_color)
                lineDataSet.setCircleColor(resources.getColor(R.color.default_main_color))
                val lineData = LineData(lineDataSet)


                val xAxis = mBinding.mLineChart.xAxis
                xAxis.position = (XAxis.XAxisPosition.BOTTOM)

                xAxis.setValueFormatter(DateValueFormatter(xEntries))

                mBinding.mLineChart.data = lineData
                mBinding.mLineChart.invalidate()

                (mBinding.mBarChart.adapter as? WeeklyProblemAdapter)?.run {
                    refreshItems(weeklyProblemNumber.value)
                    this.notifyDataSetChanged()
                }

            }

            weeklyProblemNumber.observe(viewLifecycleOwner){

                mBinding.mBarChart.invalidate()
            }

            clickLogOut.observeOnce(viewLifecycleOwner){
                val intent = LoginActivity.newIntent(requireContext())
                startActivity(intent)
                requireActivity().finish()

            }
            errorInvoked.observeOnce(viewLifecycleOwner){

            }
        }

        
    }

    internal class DateValueFormatter(private val dateList: List<String>) :
        ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase): String {

            val axisValue = value.toInt()
            return if (axisValue >= 0 && axisValue < dateList.size) {
                dateList[axisValue]
            } else {
                ""
            }
        }
    }

    private fun setupBroadcast(){

        Broadcast.apply {

            scoringCompletedBroadcast.subscribe{
                mViewModel.refresh()
            }.disposedBy(compositeDisposable)

            moveToFirstPageBroadcast.subscribe{

                if ( it == 4) {
                    moveToHomeBroadcast.onNext(Unit)
                }
            }.disposedBy(compositeDisposable)
        }
    }
    companion object {

        fun newInstance() = SettingFragment()
    }



}