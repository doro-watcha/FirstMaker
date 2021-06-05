package com.math.firstMaker.views.workBook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemWorkBookBinding
import com.math.firstMaker.model.Publish
import com.math.firstMaker.model.WorkBook
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject



/**
 * created By DORO 2020-02-28
 */

class WorkBookAdapter : RecyclerView.Adapter<WorkBookAdapter.WorkBookViewHolder>(){

    private val onClick: PublishSubject<WorkBook> = PublishSubject.create()
    val onClickEvent: Observable<WorkBook> = onClick

    private val diff = object : DiffUtil.ItemCallback<WorkBook>() {
        override fun areItemsTheSame(oldItem: WorkBook, newItem: WorkBook): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkBook, newItem: WorkBook): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<WorkBook>(this,diff)


    fun submitItems( items: List<WorkBook> ){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkBookViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkBookBinding.inflate(inflater, parent, false)

        return WorkBookViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: WorkBookViewHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class WorkBookViewHolder( private val binding: ItemWorkBookBinding) : RecyclerView.ViewHolder(binding.root){

        init {

            binding.root.setOnDebounceClickListener{
                onClick.onNext(differ.currentList[layoutPosition])
            }
        }

        fun bind ( item : WorkBook) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }

    }
}

@BindingAdapter("app:recyclerview_work_book_list")
fun RecyclerView.setWorkBookList(items: List<WorkBook>?){

    (adapter as? WorkBookAdapter)?.run {
        this.submitItems(items!!)
    }
}