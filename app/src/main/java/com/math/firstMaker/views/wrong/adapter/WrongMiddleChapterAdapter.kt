package com.math.firstMaker.views.wrong.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemBigChapterBinding
import com.math.firstMaker.databinding.ItemMiddleChapterBinding
import com.math.firstMaker.databinding.ItemWrongMiddleChapterBinding
import com.math.firstMaker.extensions.toggle
import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.MiddleChapter
import com.math.firstMaker.model.SmallChapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 2020/04/03
 */

class WrongMiddleChapterAdapter(
    val middleChapters : List<MiddleChapter>?
) : RecyclerView.Adapter<WrongMiddleChapterAdapter.MiddleChapterViewHolder>() {

    private val compositeDisposable = CompositeDisposable()

    private val diff = object : DiffUtil.ItemCallback<MiddleChapter>() {
        override fun areItemsTheSame(oldItem: MiddleChapter, newItem: MiddleChapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MiddleChapter, newItem: MiddleChapter): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<MiddleChapter>?) {
        differ.submitList(items)
    }

    private val onClick: PublishSubject<MiddleChapter> = PublishSubject.create()
    val onClickEvent: Observable<MiddleChapter> = onClick


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiddleChapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWrongMiddleChapterBinding.inflate(inflater, parent, false)

        return MiddleChapterViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: MiddleChapterViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class MiddleChapterViewHolder(private val binding: ItemWrongMiddleChapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {


        }

        fun bind(item: MiddleChapter) {

            binding.setVariable(BR.item, item)

            binding.mRecyclerView.adapter = WrongSmallChapterAdapter(item).apply {

            }


            binding.executePendingBindings()




        }
    }

}

@BindingAdapter("app:recyclerview_wrong_middle_chapter")
fun RecyclerView.setWrongMiddleChapters(items: List<MiddleChapter>?) {
    (adapter as? WrongMiddleChapterAdapter)?.run {
        this.submitItems(items)
    }
}

