package com.math.firstMaker.views.homework

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

class HomeworkBindingAdapter (private val mViewModel : HomeworkViewModel, private val mLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<HomeworkBindingAdapter.HomeworkBindingHolder>(){

    private val take: PublishSubject<Publish> = PublishSubject.create()
    val takeEvent: Observable<Publish> = take

    private val result : PublishSubject<Publish> = PublishSubject.create()
    val resultEvent : Observable<Publish> = result

    private val retake : PublishSubject<Publish> = PublishSubject.create()
    val retakeEvent : Observable<Publish> = retake

    private val partialConfirm : PublishSubject<Publish> = PublishSubject.create()
    val partialConfirmEvent : Observable<Publish> = partialConfirm

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeworkPublishBinding.inflate(inflater, parent, false)

        return HomeworkBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: HomeworkBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class HomeworkBindingHolder( private val binding: ItemHomeworkPublishBinding) : RecyclerView.ViewHolder(binding.root){

        init {


        }

        fun bind ( item : Publish) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

//
//            binding.numProblem.text = (item.homework?.problems?.size).toString() + " 문제"
//            binding.date.text = "생성시각 : " + mViewModel.getDate(item.createdAt!!)
//            binding.teacher.text = "담당 선생님 : " + item.teacher?.name


        }

    }
}

@BindingAdapter("app:recyclerview_homework_publish_list")
fun RecyclerView.setHomeworkPublishList(items: List<Publish>?){

        (adapter as? HomeworkBindingAdapter)?.run {
            this.submitItems(items!!)
        }
}
