package com.math.firstMaker.views.makeCollection.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.databinding.ItemSourceBinding
import com.math.firstMaker.model.Source
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * created By DORO 2/14/21
 */


class SourcePickerAdapter : RecyclerView.Adapter<SourcePickerAdapter.SourceHolder>(){

    private val onPick: PublishSubject<Source> = PublishSubject.create()
    val onPickEvent : Observable<Source> = onPick

    private val diff = object : DiffUtil.ItemCallback<Source>() {
        override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Source>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSourceBinding.inflate(inflater, parent, false)

        return SourceHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: SourceHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class SourceHolder( private val binding: ItemSourceBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.root.setOnClickListener {
                onPick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind ( item : Source) {
            binding.setVariable(BR.item , item)
            binding.executePendingBindings()


        }
    }
}



@BindingAdapter("app:recyclerview_source_list")
fun RecyclerView.setSources(items : List<Source>?){
    (adapter as? SourcePickerAdapter)?.run {
        this.submitItems(items)
    }
}
