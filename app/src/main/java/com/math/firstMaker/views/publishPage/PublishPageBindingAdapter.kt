package com.math.firstMaker.views.publishPage

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.databinding.ItemPublishBinding
import com.math.firstMaker.model.Problem
import com.math.firstMaker.debugE
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PublishPageBindingAdapter (private val mViewModel : PublishPageViewModel, private val mLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<PublishPageBindingAdapter.PublishPageBindingHolder>(){

    private val TAG = "PublishPageBindingAdapter"

    private val diff = object : DiffUtil.ItemCallback<Problem>() {
        override fun areItemsTheSame(oldItem: Problem, newItem: Problem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Problem, newItem: Problem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<Problem>(this,diff)

    fun submitItems( items: List<Problem> ){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublishPageBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPublishBinding.inflate(inflater, parent, false)

        return PublishPageBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PublishPageBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class PublishPageBindingHolder( private val binding: ItemPublishBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind ( item : Problem) {

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
            binding.vm = mViewModel


        }
    }
}


