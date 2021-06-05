package com.math.firstMaker.views.workBook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemWorkBookBigChapterBinding
import com.math.firstMaker.databinding.ItemWorkBookMiddleChapterBinding
import com.math.firstMaker.extensions.toggle
import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.MiddleChapter
import com.math.firstMaker.model.WorkBookMiddleChapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 1/19/21
 */

class WorkBookMiddleChapterAdapter : RecyclerView.Adapter<WorkBookMiddleChapterAdapter.WorkBookViewHolder>(){

    private val onClick: PublishSubject<WorkBookMiddleChapter> = PublishSubject.create()
    val clickEvent : Observable<WorkBookMiddleChapter> = onClick


    private val diff = object : DiffUtil.ItemCallback<WorkBookMiddleChapter>() {
        override fun areItemsTheSame(oldItem: WorkBookMiddleChapter, newItem: WorkBookMiddleChapter): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WorkBookMiddleChapter, newItem: WorkBookMiddleChapter): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<WorkBookMiddleChapter>(this,diff)


    fun submitItems( items: List<WorkBookMiddleChapter>? ){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkBookViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkBookMiddleChapterBinding.inflate(inflater, parent, false)

        return WorkBookViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: WorkBookViewHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class WorkBookViewHolder( private val binding: ItemWorkBookMiddleChapterBinding) : RecyclerView.ViewHolder(binding.root){

        init {

            binding.root.setOnDebounceClickListener{
                differ.currentList[layoutPosition].isClicked.toggle()

                if ( differ.currentList[layoutPosition].isClicked.get())
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind ( item : WorkBookMiddleChapter) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }

    }
}

@BindingAdapter("app:recyclerview_work_book_middle_chapter_list")
fun RecyclerView.setWorkBookMiddleChapterList(items: List<WorkBookMiddleChapter>?){

    (adapter as? WorkBookMiddleChapterAdapter)?.run {
        this.submitItems(items)
    }
}