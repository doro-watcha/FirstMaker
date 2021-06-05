package com.math.firstMaker.views.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.databinding.ItemBookBinding
import com.math.firstMaker.databinding.ItemTypeBinding
import com.math.firstMaker.model.Date
import com.math.firstMaker.model.Type
import com.math.firstMaker.model.WorkBook
import com.math.firstMaker.views.home.HomeViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 12/20/20
 */

class BookAdapter: RecyclerView.Adapter<BookAdapter.BookViewHolder>() {


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

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<WorkBook>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(inflater, parent, false)

        return BookViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {


        init {


        }

        fun bind ( item : WorkBook) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }

    }

}


@BindingAdapter("app:recyclerview_book_list")
fun RecyclerView.setBooks(items: List<WorkBook>?) {
    (adapter as? BookAdapter)?.run {
        this.submitItems(items)
    }
}
