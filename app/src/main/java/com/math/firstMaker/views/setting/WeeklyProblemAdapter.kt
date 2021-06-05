package com.math.firstMaker.views.setting

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.R
import com.math.firstMaker.databinding.ItemWeeklyProblemBinding
import com.math.firstMaker.model.WrongType
import com.math.firstMaker.views.wrong.WrongTypeAdapter


/**
 * created By DORO 2020/05/12
 */

class WeeklyProblemAdapter(val context : Context) : RecyclerView.Adapter<WeeklyProblemAdapter.ProblemViewHolder>(){


    var weeklyProblemNumbers : ArrayList<Int> = ArrayList()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeeklyProblemAdapter.ProblemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeeklyProblemBinding.inflate(inflater, parent, false)

        return ProblemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weeklyProblemNumbers.size
    }

    override fun onBindViewHolder(holder: WeeklyProblemAdapter.ProblemViewHolder, position: Int) {
        holder.bind(weeklyProblemNumbers[position], context)
    }

    fun refreshItems(items: ArrayList<Int>?) {
        this.weeklyProblemNumbers = items ?: ArrayList()
    }

    inner class ProblemViewHolder ( private val binding : ItemWeeklyProblemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind ( value : Int, context : Context ) {

            binding.setVariable(BR.item, value)
            binding.executePendingBindings()

            val layoutParams = ConstraintLayout.LayoutParams(25.dp,value.dp)
            layoutParams.startToStart = R.id.txt_problem_number
            layoutParams.endToEnd = R.id.txt_problem_number
            layoutParams.bottomToTop = R.id.txt_day
            layoutParams.bottomMargin = 10.dp

            binding.barChart.layoutParams = layoutParams
            when ( layoutPosition) {
                0 -> binding.txtDay.text = "SUN"

                1 -> binding.txtDay.text = "MON"

                2 -> binding.txtDay.text = "TUE"

                3 -> binding.txtDay.text = "WED"

                4 -> binding.txtDay.text = "THU"

                5 -> binding.txtDay.text = "FRI"

                6 -> binding.txtDay.text = "SAT"

            }

        }
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

}

@BindingAdapter("app:recyclerview_weekly_problems")
fun RecyclerView.setWeeklyProblems(items : ArrayList<Int>){


    (adapter as? WeeklyProblemAdapter)?.run {
        this.weeklyProblemNumbers = items
    }
}