package com.math.firstMaker.views.exam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.databinding.ItemExamBinding
import com.math.firstMaker.databinding.ItemExamPublishBinding
import com.math.firstMaker.databinding.ItemHomeworkPublishBinding
import com.math.firstMaker.model.Exam
import com.math.firstMaker.model.Homework
import com.math.firstMaker.model.Publish
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ExamBindingAdapter (private val mViewModel : ExamViewModel, private val mLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<ExamBindingAdapter.ExamBindingHolder>(){

    private val goExam: PublishSubject<Publish> = PublishSubject.create()
    val goExamEvent: Observable<Publish> = goExam

    private val goResult: PublishSubject<Publish> = PublishSubject.create()
    val goResultEvent: Observable<Publish> = goResult

    private val retake: PublishSubject<Publish> = PublishSubject.create()
    val retakeEvent : Observable<Publish> = retake

    private val diff = object : DiffUtil.ItemCallback<Publish>() {
        override fun areItemsTheSame(oldItem: Publish, newItem: Publish): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Publish, newItem: Publish): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<Publish>(this,diff)


    fun submitItems( items: List<Publish> ){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExamPublishBinding.inflate(inflater, parent, false)

        return ExamBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ExamBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class ExamBindingHolder( private val binding: ItemExamPublishBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.goExam.setOnClickListener{
                goExam.onNext(differ.currentList[layoutPosition])
            }
            binding.goResult.setOnClickListener{
                goResult.onNext(differ.currentList[layoutPosition])
            }
            binding.retake.setOnClickListener {
                retake.onNext(differ.currentList[layoutPosition])
            }
        }

        fun bind ( item : Publish) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
            binding.vm = mViewModel

            if ( item.state == "submitted") {
                binding.goExam.visibility = View.GONE
                binding.goResult.visibility = View.VISIBLE
                binding.retake.visibility = View.VISIBLE

            }



        }

    }
}

@BindingAdapter("app:recyclerview_exam_publish_list")
fun RecyclerView.setExamPublishList(items: List<Publish>?){

    (adapter as? ExamBindingAdapter)?.run {
        this.submitItems(items!!)
    }
}
