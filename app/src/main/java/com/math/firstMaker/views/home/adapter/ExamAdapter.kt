package com.math.firstMaker.views.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemExamBinding
import com.math.firstMaker.model.Exam
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 12/20/20
 */

class ExamAdapter: RecyclerView.Adapter<ExamAdapter.ExamViewHolder>() {


    private val onClick: PublishSubject<Exam> = PublishSubject.create()
    val onClickEvent: Observable<Exam> = onClick

    private val diff = object : DiffUtil.ItemCallback<Exam>() {
        override fun areItemsTheSame(oldItem: Exam, newItem: Exam): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Exam, newItem: Exam): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Exam>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExamBinding.inflate(inflater, parent, false)

        return ExamViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ExamViewHolder(private val binding: ItemExamBinding) : RecyclerView.ViewHolder(binding.root) {


        init {

            binding.root.setOnDebounceClickListener {
                onClick.onNext(differ.currentList[layoutPosition])
            }


        }

        fun bind ( item : Exam) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }

    }

}


@BindingAdapter("app:recyclerview_exam_list")
fun RecyclerView.setExams(items: List<Exam>?) {
    (adapter as? ExamAdapter)?.run {
        this.submitItems(items)
    }
}