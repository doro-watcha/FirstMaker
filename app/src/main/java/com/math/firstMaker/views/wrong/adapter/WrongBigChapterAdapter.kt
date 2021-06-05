package com.math.firstMaker.views.wrong.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemBigChapterBinding
import com.math.firstMaker.databinding.ItemWrongBigChapterBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.model.BigChapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 2020/04/03
 */

class WrongBigChapterAdapter(
) : RecyclerView.Adapter<WrongBigChapterAdapter.BigChapterViewHolder>() {

    private val compositeDisposable = CompositeDisposable()

    private val onClick: PublishSubject<BigChapter> = PublishSubject.create()
    val onClickEvent: Observable<BigChapter> = onClick


    private val diff = object : DiffUtil.ItemCallback<BigChapter>() {
        override fun areItemsTheSame(oldItem: BigChapter, newItem: BigChapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BigChapter, newItem: BigChapter): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<BigChapter>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BigChapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWrongBigChapterBinding.inflate(inflater, parent, false)

        return BigChapterViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: BigChapterViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class BigChapterViewHolder(private val binding: ItemWrongBigChapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.root.setOnDebounceClickListener{
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind(item: BigChapter) {

            binding.setVariable(BR.item, item)

            binding.mRecyclerView.adapter = WrongMiddleChapterAdapter(differ.currentList[layoutPosition].middleChapters)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_wrong_big_chapter")
fun RecyclerView.setWrongBigChapters(items: List<BigChapter>?) {
    (adapter as? WrongBigChapterAdapter)?.run {
        debugE("Good",items?.size)
        this.submitItems(items)
    }
}
