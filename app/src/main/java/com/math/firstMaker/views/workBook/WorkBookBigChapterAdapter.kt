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
import com.math.firstMaker.databinding.ItemWorkBookBinding
import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.Publish
import com.math.firstMaker.model.WorkBook
import com.math.firstMaker.model.WorkBookBigChapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 1/13/21
 */

class WorkBookBigChapterAdapter : RecyclerView.Adapter<WorkBookBigChapterAdapter.WorkBookViewHolder>(){

    private val onClick: PublishSubject<WorkBookBigChapter> = PublishSubject.create()
    val clickEvent: Observable<WorkBookBigChapter> = onClick

    private val onClickBuy : PublishSubject<WorkBookBigChapter> = PublishSubject.create()
    val clickBuy : Observable<WorkBookBigChapter> = onClickBuy


    private val diff = object : DiffUtil.ItemCallback<WorkBookBigChapter>() {
        override fun areItemsTheSame(oldItem: WorkBookBigChapter, newItem: WorkBookBigChapter): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WorkBookBigChapter, newItem: WorkBookBigChapter): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<WorkBookBigChapter>(this,diff)


    fun submitItems( items: List<WorkBookBigChapter> ){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkBookViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkBookBigChapterBinding.inflate(inflater, parent, false)

        return WorkBookViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: WorkBookViewHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class WorkBookViewHolder( private val binding: ItemWorkBookBigChapterBinding) : RecyclerView.ViewHolder(binding.root){

        init {

            binding.root.setOnDebounceClickListener{
                onClick.onNext(differ.currentList[layoutPosition])
            }

            binding.txtBuy.setOnDebounceClickListener {
                onClickBuy.onNext(differ.currentList[layoutPosition])
            }
        }

        fun bind ( item : WorkBookBigChapter) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }

    }
}

@BindingAdapter("app:recyclerview_work_book_big_chapter_list")
fun RecyclerView.setWorkBookBigChapterList(items: List<WorkBookBigChapter>?){

    (adapter as? WorkBookBigChapterAdapter)?.run {
        this.submitItems(items!!)
    }
}