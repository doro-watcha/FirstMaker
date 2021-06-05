package com.math.firstMaker.views.review

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
import com.math.firstMaker.model.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ReviewBindingAdapter (private val mViewModel : ReviewViewModel, private val editMode : Boolean): RecyclerView.Adapter<ReviewBindingAdapter.ReviewBindingHolder>(){

    private val onRemove: PublishSubject<Problem> = PublishSubject.create()
    val onRemoveEvent : Observable<Problem> = onRemove

    private val onBlackList : PublishSubject<Problem> = PublishSubject.create()
    val onBlackListEvent : Observable<Problem> = onBlackList

    private val diff = object : DiffUtil.ItemCallback<Problem>() {
        override fun areItemsTheSame(oldItem: Problem, newItem: Problem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Problem, newItem: Problem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<Problem>(this,diff)


    fun submitItems( items: List<Problem>? ){
        differ.submitList(items)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewProblemBinding.inflate(inflater, parent, false)

        return ReviewBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ReviewBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class ReviewBindingHolder( private val binding: ItemReviewProblemBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.btnRemove.setOnClickListener {
                onRemove.onNext(differ.currentList[layoutPosition])
            }
            binding.btnBlackList.setOnClickListener {
                onBlackList.onNext(differ.currentList[layoutPosition])
            }
        }

        fun bind ( item : Problem ) {
            binding.setVariable(BR.item , item)
            binding.executePendingBindings()

            if ( !editMode ) {
                binding.btnBlackList.visibility = View.GONE
                binding.btnRemove.visibility = View.GONE



            }
        }
    }
}

@BindingAdapter("app:recyclerview_review_problem_list")
fun RecyclerView.setReviewProblem(items: List<Problem>?){

    (adapter as? ReviewBindingAdapter)?.run {
        this.submitItems(items)
    }
}

