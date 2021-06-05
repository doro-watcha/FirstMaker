package com.math.firstMaker.views.home.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.R
import com.math.firstMaker.databinding.ItemNoteBinding
import com.math.firstMaker.databinding.ItemProblemBinding
import com.math.firstMaker.extensions.toggle
import com.math.firstMaker.model.Note
import com.math.firstMaker.views.publishPage.ProblemBindingAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.get


/**
 * created By DORO 1/11/21
 */

class NoteListAdapter(val context : Context) :
    RecyclerView.Adapter<NoteListAdapter.Holder>() {

    private val TAG = NoteListAdapter::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()


    private val onClickStar: PublishSubject<Pair<Note, Int>> = PublishSubject.create()
    val onClickStarEvent: Observable<Pair<Note, Int>> = onClickStar


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
        val binding = ItemNoteBinding.inflate(inflater, parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(differ.currentList[position])

    inner class Holder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) ,
        KoinComponent {

        init {

        }

        fun bind(item: Note) {

            val dateParserUtil = get<DateParserUtil>()

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.title.text = (layoutPosition + 1).toString() + "번 문제 " + item.problem?.bigChapter?.name


            binding.txtSpendingTime.text = dateParserUtil.calculateTimeBySecond(item.spendingTime.get())


        }
    }
}

@BindingAdapter("app:recyclerview_note_detail_list")
fun RecyclerView.setNoteList(items: List<Note>?) {
    (adapter as? NoteListAdapter)?.run {
        this.submitItems(items)
    }
}