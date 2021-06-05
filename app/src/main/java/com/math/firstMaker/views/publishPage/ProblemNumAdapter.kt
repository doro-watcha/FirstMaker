package com.math.firstMaker.views.publishPage

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.rxRepeatTimer
import com.math.firstMaker.databinding.ItemProblemNumBinding
import com.math.firstMaker.model.Problem
import com.math.firstMaker.debugE
import com.math.firstMaker.model.Note
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import okhttp3.internal.notify

class ProblemNumAdapter(val context: Context) : RecyclerView.Adapter<ProblemNumAdapter.Holder>() {


    private val TAG = "ProblemNumAdapter"
    private val compositeDisposable = CompositeDisposable()
    var curProblem = 0
    var isSubmitted = false

    private val onClick: PublishSubject<Int> = PublishSubject.create()
    val onClickEvent: Observable<Int> = onClick

    private val diff = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Note>?) {
        differ.submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProblemNumBinding.inflate(inflater, parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(differ.currentList[position])


    inner class Holder(val binding : ItemProblemNumBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

                binding.root.background = ContextCompat.getDrawable(context, R.drawable.cur_problem_border)

                notifyDataSetChanged()
                curProblem = layoutPosition
                onClick.onNext(curProblem)
            }
        }

        fun bind( item : Note) {


            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.text.text = (layoutPosition+1).toString()

            if ( curProblem == layoutPosition) {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.cur_problem_border)

                debugE(TAG, "if adapter $layoutPosition")


            } else {

                debugE(TAG, "adapter $layoutPosition")
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.rounded_button6)

            }

        }
    }
}

@BindingAdapter("app:recyclerview_problem_num")
fun RecyclerView.setProblemNum(items: List<Note>?) {
    (adapter as? ProblemNumAdapter)?.run {
        this.submitItems(items)
    }
}