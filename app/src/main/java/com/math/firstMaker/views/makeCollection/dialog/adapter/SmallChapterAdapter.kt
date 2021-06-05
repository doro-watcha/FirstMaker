package com.math.firstMaker.views.makeCollection.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemSmallChapterBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.extensions.toggle
import com.math.firstMaker.model.MiddleChapter
import com.math.firstMaker.model.SmallChapter
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 2020/04/03
 */

class SmallChapterAdapter(
    private val item : MiddleChapter
) : RecyclerView.Adapter<SmallChapterAdapter.SmallChapterViewHolder>() {

    private val TAG = SmallChapterAdapter::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private val diff = object : DiffUtil.ItemCallback<SmallChapter>() {
        override fun areItemsTheSame(oldItem: SmallChapter, newItem: SmallChapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SmallChapter, newItem: SmallChapter): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<SmallChapter>?) {
        differ.submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallChapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSmallChapterBinding.inflate(inflater, parent, false)

        return SmallChapterViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: SmallChapterViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class SmallChapterViewHolder(private val binding: ItemSmallChapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.btnSelect.setOnDebounceClickListener {

                debugE(TAG, differ.currentList[layoutPosition])
                // 눌려있는것을 해제할때
                if ( differ.currentList[layoutPosition].isSelected.get()) {
                    Broadcast.deleteSmallChapterBroadcast.onNext(differ.currentList[layoutPosition])
                }
                // 새로 클릭할때
                else {
                    Broadcast.clickSmallChapterBroadcast.onNext(differ.currentList[layoutPosition])
                }
                differ.currentList[layoutPosition].isSelected.toggle()

            }


        }

        fun bind(item: SmallChapter) {

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }
    }
}

@BindingAdapter("app:recyclerview_small_chapter")
fun RecyclerView.setSmallChapters(items: List<SmallChapter>?) {
    (adapter as? SmallChapterAdapter)?.run {
        this.submitItems(items)
    }
}

