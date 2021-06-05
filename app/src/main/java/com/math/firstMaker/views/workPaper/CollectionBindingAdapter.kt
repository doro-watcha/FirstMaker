package com.math.firstMaker.views.workPaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.databinding.*
import com.math.firstMaker.model.Exam
import com.math.firstMaker.model.Homework
import com.math.firstMaker.model.WorkPaper
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CollectionBindingAdapter (private val mViewModel : WorkPaperViewModel, private val mLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<CollectionBindingAdapter.CollectionBindingHolder>(){

    private val take: PublishSubject<WorkPaper> = PublishSubject.create()
    val takeEvent: Observable<WorkPaper> = take


    private val diff = object : DiffUtil.ItemCallback<WorkPaper>() {
        override fun areItemsTheSame(oldItem: WorkPaper, newItem: WorkPaper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkPaper, newItem: WorkPaper): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<WorkPaper>(this,diff)


    fun submitItems( items: List<WorkPaper> ){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkPaperBinding.inflate(inflater, parent, false)

        return CollectionBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CollectionBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class CollectionBindingHolder( private val binding: ItemWorkPaperBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.goWorkPaper.setOnClickListener{
                take.onNext(differ.currentList[layoutPosition])
            }
        }

        fun bind ( item : WorkPaper) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
            binding.vm = mViewModel

         //   binding.numProblem.text = ( item.problems.size ).toString() + " 문제"

        }

    }
}

@BindingAdapter("app:recyclerview_work_paper_list")
fun RecyclerView.setCollectionWorkPaprList(items: List<WorkPaper>?){

    (adapter as? CollectionBindingAdapter)?.run {
        this.submitItems(items!!)
    }
}
