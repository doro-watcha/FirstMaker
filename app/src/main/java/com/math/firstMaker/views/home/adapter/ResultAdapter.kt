package com.math.firstMaker.views.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.databinding.ItemProblemBinding
import com.math.firstMaker.databinding.ItemResultBinding
import com.math.firstMaker.model.Note
import com.math.firstMaker.views.publishPage.ProblemBindingAdapter
import com.math.firstMaker.views.publishPage.PublishPageViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.get
import java.nio.file.Paths.get


/**
 * created By DORO 12/22/20
 */

class ResultAdapter :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private val compositeDisposable = CompositeDisposable()

    private val onClickItem: PublishSubject<Int> = PublishSubject.create()
    val onClickItemEvent: Observable<Int> = onClickItem




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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemResultBinding.inflate(inflater, parent, false)

        return ResultViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    inner class ResultViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) , KoinComponent{

        init {
            binding.root.setOnClickListener {
                onClickItem.onNext(layoutPosition)
            }
        }

        fun bind(item: Note) {

            val dateParserUtil = get<DateParserUtil>()

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.txtProblem.text = (layoutPosition + 1).toString() + " 번 문제"

//            binding.txtResult.text = if ( item.submit.get() == item.problem?.answer  ) "맞음" else "틀림"

//            if ( item.submit.get() == "0" || item.submit.get() == null) binding.txtResult.text = "채점안됨"

            binding.txtSpendingTime.text = dateParserUtil.calculateTimeBySecond(item.spendingTime.get())



        }
    }
}

@BindingAdapter("app:recyclerview_results")
fun RecyclerView.setResults(items: List<Note>?) {
    (adapter as? ResultAdapter)?.run {
        this.submitItems(items)
    }
}