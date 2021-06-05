package com.math.firstMaker.views.wrong

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.MainApplication.Companion.context
import com.math.firstMaker.R
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemWrongTypeBinding
import com.math.firstMaker.model.WrongType
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 2020/04/03
 */

class WrongTypeAdapter (private val mViewModel : WrongStorageViewModel): RecyclerView.Adapter<WrongTypeAdapter.WrongTypeHolder>(){

    var curType = -1

    private val onClick: PublishSubject<WrongType> = PublishSubject.create()
    val onClickEvent: Observable<WrongType> = onClick


    private val diff = object : DiffUtil.ItemCallback<WrongType>() {
        override fun areItemsTheSame(oldItem: WrongType, newItem: WrongType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WrongType, newItem: WrongType): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<WrongType>(this,diff)


    fun submitItems( items: List<WrongType> ){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrongTypeHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWrongTypeBinding.inflate(inflater, parent, false)

        return WrongTypeHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: WrongTypeHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class WrongTypeHolder( private val binding: ItemWrongTypeBinding) : RecyclerView.ViewHolder(binding.root){

        init {

            binding.root.setOnDebounceClickListener{
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.white_layout_colored_rounded_border)
                binding.title.setTextColor(ContextCompat.getColor(context, R.color.default_main_color_2))

                curType = layoutPosition
                onClick.onNext(differ.currentList[layoutPosition])

                notifyDataSetChanged()

            }

        }

        fun bind ( item : WrongType ) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
            binding.vm = mViewModel

            if ( curType == layoutPosition ) {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.white_layout_colored_rounded_border)
                binding.title.setTextColor(ContextCompat.getColor(context, R.color.default_main_color_2))
            } else {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.white_layout_border_rounded)
                binding.title.setTextColor(ContextCompat.getColor(context, R.color.default_text_color))
            }

        }
    }
}

@BindingAdapter("app:recyclerview_wrong_type")
fun RecyclerView.setWrongType(items : List<WrongType>?){


    (adapter as? WrongTypeAdapter)?.run {
        this.submitItems(items!!)
    }
}