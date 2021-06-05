package com.math.firstMaker.views.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemHomeworkBinding
import com.math.firstMaker.databinding.ItemPaperBinding
import com.math.firstMaker.model.Homework
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 12/20/20
 */


class HomeworkAdapter: RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder>() {


    private val onClick: PublishSubject<Homework> = PublishSubject.create()
    val onClickEvent: Observable<Homework> = onClick

    private val diff = object : DiffUtil.ItemCallback<Homework>() {
        override fun areItemsTheSame(oldItem: Homework, newItem: Homework): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Homework, newItem: Homework): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Homework>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeworkBinding.inflate(inflater, parent, false)

        return HomeworkViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: HomeworkViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class HomeworkViewHolder(private val binding: ItemHomeworkBinding) : RecyclerView.ViewHolder(binding.root) {


        init {

            binding.root.setOnDebounceClickListener{
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind ( item : Homework) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }

    }

}


@BindingAdapter("app:recyclerview_homework_list")
fun RecyclerView.setHomeworks(items: List<Homework>?) {
    (adapter as? HomeworkAdapter)?.run {
        this.submitItems(items)
    }
}