package com.math.firstMaker.views.result

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.databinding.ItemResultBinding
import com.math.firstMaker.model.Note
import com.math.firstMaker.model.Problem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ResultBindingAdapter (private val mViewModel : ResultViewModel, private val mLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<ResultBindingAdapter.ExamResultBindingHolder>(){


    private val problemSolutionView: PublishSubject<Problem?> = PublishSubject.create()
    val problemSolutionViewEvent : Observable<Problem?> = problemSolutionView


    private val diff = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<Note>(this,diff)


    fun submitItems( items: List<Note> ){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamResultBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemResultBinding.inflate(inflater, parent, false)

        return ExamResultBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ExamResultBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class ExamResultBindingHolder ( private val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind ( item : Note ) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

//            var state : String
//            if ( item.state == "correct") state = "??????"
//            else if ( item.state == "incorrect" ) state = "??????"
//            else if ( item.state == "unconfirmed" ) state = "????????????"
//            else state = "????????????"
//
//            binding.problemIndex.text = item.problem.id.toString() + " ??? ??????"
//            binding.isCorrect.text = state
//            binding.answer.text = "?????? " + item.problem.answer.toString()
//            binding.submit.text = "?????? " + item.submit

        }
    }
}

