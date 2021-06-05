package com.math.firstMaker.views.makeCollection.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.databinding.ItemSmallChapterListBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.model.SmallChapter

/**
 * created By DORO 12/17/20
 */

class StringAdapter(
) : RecyclerView.Adapter<StringAdapter.StringViewHolder>() {

    private val TAG = StringAdapter::class.java.simpleName

    var items : List<SmallChapter> = listOf()

    fun submitItems(items: List<SmallChapter>?) {
        debugE(TAG, items)
        this.items = items ?: listOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSmallChapterListBinding.inflate(inflater, parent, false)

        return StringViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class StringViewHolder(private val binding: ItemSmallChapterListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {


        }

        fun bind(item: SmallChapter) {

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }
    }

}

@BindingAdapter("app:recyclerview_small_chapter_list")
fun RecyclerView.setStringList(items: List<SmallChapter>?) {
    (adapter as? StringAdapter)?.run {
        this.submitItems(items)
    }
}
