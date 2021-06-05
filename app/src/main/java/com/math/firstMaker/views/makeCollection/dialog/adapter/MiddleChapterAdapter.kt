package com.math.firstMaker.views.makeCollection.dialog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemMiddleChapterBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.extensions.toggle
import com.math.firstMaker.model.MiddleChapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.core.context.GlobalContext.get


/**
 * created By DORO 2020/04/03
 */

class MiddleChapterAdapter( val context: Context
) : RecyclerView.Adapter<MiddleChapterAdapter.MiddleChapterViewHolder>() {

    private val TAG = MiddleChapterAdapter::class.java.simpleName


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
        val binding = ItemMiddleChapterBinding.inflate(inflater, parent, false)

        return MiddleChapterViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: MiddleChapterViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class MiddleChapterViewHolder(private val binding: ItemMiddleChapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.btnSelect.setOnDebounceClickListener {

                debugE(TAG, differ.currentList[layoutPosition])
                // 눌려있는것을 해제할때
                if ( differ.currentList[layoutPosition].isSelected()) {

                    differ.currentList[layoutPosition].smallChapters?.forEach { smallChapter ->
                        if (smallChapter.isSelected.get()) {
                            Broadcast.deleteSmallChapterBroadcast.onNext(smallChapter)
                            smallChapter.isSelected.set(false)
                        }
                    }

                    binding.btnSelect.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.chapter_not_selected))

                }
                // 새로 클릭할때
                else {
                    differ.currentList[layoutPosition].smallChapters?.forEach { smallChapter ->

                        if ( !smallChapter.isSelected.get()) {
                            Broadcast.clickSmallChapterBroadcast.onNext(smallChapter)
                            smallChapter.isSelected.set(true)
                        }
                    }

                    binding.btnSelect.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.chapter_selected))
                }
//                differ.currentList[layoutPosition].isSelected.toggle()

            }



        }

        fun bind(item: MiddleChapter) {

            binding.setVariable(BR.item, item)

            binding.mRecyclerView.adapter = SmallChapterAdapter(item).apply {

            }


            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_middle_chapter")
fun RecyclerView.setMiddleChapters(items: List<MiddleChapter>?) {
    (adapter as? MiddleChapterAdapter)?.run {
        this.submitItems(items)
    }
}

