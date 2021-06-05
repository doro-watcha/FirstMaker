package com.math.firstMaker.views.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.MainApplication.Companion.context
import com.math.firstMaker.R
import com.math.firstMaker.databinding.ItemTypeBinding
import com.math.firstMaker.model.Date
import com.math.firstMaker.model.Type
import com.math.firstMaker.views.home.HomeViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 2020-03-22
 */

class TypeAdapter(
    private val mViewModel: HomeViewModel,
    private val mLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {


    private val onClick: PublishSubject<Type> = PublishSubject.create()
    val onClickEvent: Observable<Type> = onClick

    var curType = ""

    private val diff = object : DiffUtil.ItemCallback<Type>() {
        override fun areItemsTheSame(oldItem: Type, newItem: Type): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Type, newItem: Type): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Type>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTypeBinding.inflate(inflater, parent, false)

        return TypeViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class TypeViewHolder(private val binding: ItemTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.root.setOnClickListener {
//                if (layoutPosition != curType) {
//                    binding.txtType.setTextColor(ContextCompat.getColor(context,R.color.thin_gray_text))
//                    binding.root.background = ContextCompat.getDrawable(context,R.drawable.rounded_button5)
//                    notifyDataSetChanged()
//                    curType = layoutPosition
//                }
                onClick.onNext(differ.currentList[layoutPosition])
            }
        }

        fun bind(item: Type) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


//            if ( curType == layoutPosition) {
//                binding.txtType.setTextColor(Color.WHITE)
//                binding.root.background = ContextCompat.getDrawable(context, R.drawable.rounded_button4)
//            } else {
//                binding.txtType.setTextColor(ContextCompat.getColor(context ,R.color.thin_gray_text))
//                binding.root.background = ContextCompat.getDrawable(context, R.drawable.rounded_button5)
//            }

        }
    }

}

@BindingAdapter("app:recyclerview_type_list")
fun RecyclerView.setTypes(items: List<Type>) {
    (adapter as? TypeAdapter)?.run {
        this.submitItems(items)
    }
}
