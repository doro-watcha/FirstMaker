package com.math.firstMaker.views.home.classList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemClassBinding
import com.math.firstMaker.databinding.ItemSearchResultBinding
import com.math.firstMaker.model.Class
import com.math.firstMaker.model.Problem
import com.math.firstMaker.views.makeCollection.MakeCollectionViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 1/3/21
 */

class ClassBindingAdapter : RecyclerView.Adapter<ClassBindingAdapter.ClassViewHolder>() {

    private val compositeDisposable = CompositeDisposable()

    private val onClick: PublishSubject<Class> = PublishSubject.create()
    val onClickEvent: Observable<Class> = onClick

    private val diff = object : DiffUtil.ItemCallback<Class>() {
        override fun areItemsTheSame(oldItem: Class, newItem: Class): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Class, newItem: Class): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Class>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClassBinding.inflate(inflater, parent, false)

        return ClassViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ClassViewHolder(private val binding: ItemClassBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.root.setOnDebounceClickListener {
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind(item: Class) {

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_classes")
fun RecyclerView.setClasses(items: List<Class>?) {
    (adapter as? ClassBindingAdapter)?.run {
        this.submitItems(items)
    }
}

