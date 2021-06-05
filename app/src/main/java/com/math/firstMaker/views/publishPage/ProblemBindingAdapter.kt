package com.math.firstMaker.views.publishPage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.ItemProblemBinding
import com.math.firstMaker.model.Problem
import com.jakewharton.rxbinding3.widget.textChanges
import com.math.firstMaker.R
import com.math.firstMaker.common.increment
import com.math.firstMaker.common.rxRepeatTimer
import com.math.firstMaker.debugE
import com.math.firstMaker.extensions.toggle
import com.math.firstMaker.model.Note
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import net.bytebuddy.implementation.Implementation
import okhttp3.internal.notify
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/04/02
 */


class ProblemBindingAdapter(val context : Context) :
    RecyclerView.Adapter<ProblemBindingAdapter.Holder>() {

    private val TAG = ProblemBindingAdapter::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()


    private val onClickStar: PublishSubject<Pair<Note,Int>> = PublishSubject.create()
    val onClickStarEvent: Observable<Pair<Note,Int>> = onClickStar


    private val diff = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Note>?) {
        differ.submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProblemBinding.inflate(inflater, parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(differ.currentList[position])

    inner class Holder(val binding: ItemProblemBinding) : RecyclerView.ViewHolder(binding.root) ,KoinComponent {

        init {

            binding.greenStar.setOnClickListener {
                differ.currentList[layoutPosition].isGreenStarClicked.toggle()
                onClickStar.onNext(Pair(differ.currentList[layoutPosition],layoutPosition))
            }
        }

        fun bind(item: Note) {

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.title.text = (layoutPosition + 1).toString() + "번 문제 " + item.problem?.bigChapter?.name

            val textColor = when ( item.status) {

                "맞음" -> R.color.blue
                "틀림" -> R.color.colorRed
                else -> R.color.black

            }

            binding.title.setTextColor(context.resources.getColor(textColor))

            binding.editSubmit.setOnFocusChangeListener { v, hasFocus ->
                if( hasFocus) {
                    binding.editSubmit.setText("")
                }
            }


        }
    }
}

@BindingAdapter("app:recyclerview_notes")
fun RecyclerView.setNotes(items: List<Note>?) {
    (adapter as? ProblemBindingAdapter)?.run {
        this.submitItems(items)
    }
}