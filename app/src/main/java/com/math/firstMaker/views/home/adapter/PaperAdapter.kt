package com.math.firstMaker.views.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemPaperBinding

import com.math.firstMaker.model.WorkPaper
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 12/20/20
 */

class PaperAdapter: RecyclerView.Adapter<PaperAdapter.PaperViewHolder>() {


    private val onClick: PublishSubject<WorkPaper> = PublishSubject.create()
    val onClickEvent: Observable<WorkPaper> = onClick

    private val diff = object : DiffUtil.ItemCallback<WorkPaper>() {
        override fun areItemsTheSame(oldItem: WorkPaper, newItem: WorkPaper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkPaper, newItem: WorkPaper): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<WorkPaper>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaperViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPaperBinding.inflate(inflater, parent, false)

        return PaperViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: PaperViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class PaperViewHolder(private val binding: ItemPaperBinding) : RecyclerView.ViewHolder(binding.root) {


        init {

            binding.root.setOnDebounceClickListener{
                onClick.onNext(differ.currentList[layoutPosition])
            }


        }

        fun bind ( item : WorkPaper) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }

    }

}
@BindingAdapter("app:recyclerview_paper_list")
fun RecyclerView.setPapers(items: List<WorkPaper>?) {
    (adapter as? PaperAdapter)?.run {
        this.submitItems(items)
    }
}
