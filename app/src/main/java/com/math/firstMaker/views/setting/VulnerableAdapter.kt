package com.math.firstMaker.views.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemVulnerableChapterBinding
import com.math.firstMaker.model.BigChapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 2020/05/11
 */



class VulnerableAdapter : RecyclerView.Adapter<VulnerableAdapter.VulnerableHolder>(){


    private val onClickEvent: PublishSubject<BigChapter> = PublishSubject.create()
    val clickEvent : Observable<BigChapter> = onClickEvent


    private val diff = object : DiffUtil.ItemCallback<BigChapter>() {
        override fun areItemsTheSame(oldItem: BigChapter, newItem: BigChapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BigChapter, newItem: BigChapter): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<BigChapter>(this,diff)


    fun submitItems( items: List<BigChapter>? ){
        differ.submitList(items)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VulnerableHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVulnerableChapterBinding.inflate(inflater, parent, false)

        return VulnerableHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: VulnerableHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class VulnerableHolder( private val binding: ItemVulnerableChapterBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.root.setOnDebounceClickListener {
                onClickEvent.onNext(differ.currentList[layoutPosition])
            }
        }

        fun bind ( item : BigChapter ) {
            binding.setVariable(BR.item , item)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_vulnerable_chapter")
fun RecyclerView.setHomework(items: List<BigChapter>?){

    (adapter as? VulnerableAdapter)?.run {
        this.submitItems(items)
    }
}

