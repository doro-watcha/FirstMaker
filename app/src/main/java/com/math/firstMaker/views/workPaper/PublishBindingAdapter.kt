package com.math.firstMaker.views.workPaper

import android.view.LayoutInflater
import android.view.View
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
import com.math.firstMaker.model.Publish
import com.math.firstMaker.model.WorkPaper
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PublishBindingAdapter (private val mViewModel : WorkPaperViewModel, private val mLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<PublishBindingAdapter.PublishBindingHolder>(){

    private val resume: PublishSubject<Publish> = PublishSubject.create()
    val resumeEvent: Observable<Publish> = resume

    private val retake : PublishSubject<Publish> = PublishSubject.create()
    val retakeEvent : Observable<Publish> = retake

    private val result : PublishSubject<Publish> = PublishSubject.create()
    val resultEvent : Observable<Publish> = result

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublishBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkPaperPublishBinding.inflate(inflater, parent, false)

        return PublishBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PublishBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class PublishBindingHolder( private val binding: ItemWorkPaperPublishBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.resume.setOnClickListener {
                resume.onNext(differ.currentList[layoutPosition])
            }
            binding.result.setOnClickListener {
                result.onNext(differ.currentList[layoutPosition])
            }
            binding.retake.setOnClickListener {
                retake.onNext(differ.currentList[layoutPosition])
            }
            binding.partialConfirmed.setOnClickListener {
                partialConfirm.onNext(differ.currentList[layoutPosition])
            }
        }

        fun bind ( item : Publish) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
            binding.vm = mViewModel

        //    binding.numProblem.text = (item.workpaper?.problems?.size).toString() + " 문제"

            when ( item.state ) {
                "published" -> {
                    binding.state.text = "대기중"
                    binding.result.visibility = View.GONE
                    binding.retake.visibility = View.GONE
                    binding.resume.visibility = View.VISIBLE
                }
                "saved" -> {
                    binding.state.text = "진행중"
                    binding.resume.visibility = View.VISIBLE
                    binding.result.visibility = View.GONE
                    binding.retake.visibility = View.GONE
                }
                "confirmed" -> {
                    binding.state.text = "완료됨"
                    binding.resume.visibility = View.GONE
                    binding.result.visibility = View.VISIBLE
                    binding.retake.visibility = View.VISIBLE
                    binding.partialConfirmed.visibility = View.GONE
                }
                "partial-confirmed" -> {
                    binding.state.text= "진행중 (중간채점됨)"
                    binding.resume.visibility = View.VISIBLE
                    binding.result.visibility = View.GONE
                    binding.retake.visibility = View.GONE
                    binding.partialConfirmed.visibility = View.VISIBLE
                }

            }

        }

    }
}

@BindingAdapter("app:recyclerview_workpaperk_publish_list")
fun RecyclerView.setHomeworkPublishList(items: List<Publish>?){

    (adapter as? PublishBindingAdapter)?.run {
        this.submitItems(items!!)
    }
}
