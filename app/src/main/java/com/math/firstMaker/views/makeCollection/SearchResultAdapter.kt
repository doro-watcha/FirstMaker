package com.math.firstMaker.views.makeCollection


/**
 * created By DORO 2020/04/04
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemProblemSetBinding
import com.math.firstMaker.databinding.ItemSearchResultBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.model.Problem
import com.math.firstMaker.model.ProblemSet
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SearchResultAdapter (private val mViewModel : MakeCollectionViewModel, private val mLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder>(){

    private val TAG = SearchResultAdapter::class.java.simpleName

    private val onRemove: PublishSubject<ProblemSet> = PublishSubject.create()
    val onRemoveEvent : Observable<ProblemSet> = onRemove

    private val onClickEvent : PublishSubject<ProblemSet> = PublishSubject.create()
    val clickEvent : Observable<ProblemSet> = onClickEvent

    private val diff = object : DiffUtil.ItemCallback<ProblemSet>() {
        override fun areItemsTheSame(oldItem: ProblemSet, newItem: ProblemSet): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProblemSet, newItem: ProblemSet): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<ProblemSet>(this,diff)


    fun submitItems( items: List<ProblemSet>? ){
        differ.submitList(items)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchResultBinding.inflate(inflater, parent, false)

        return SearchResultHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class SearchResultHolder( private val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root){

        init{

            binding.root.setOnDebounceClickListener{
                debugE(TAG, differ.currentList[layoutPosition])
                onClickEvent.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind ( item : ProblemSet ) {
            binding.setVariable(BR.item , item)
            binding.executePendingBindings()


        }
    }
}

@BindingAdapter("app:recyclerview_problem_set_list")
fun RecyclerView.setProblemSetList(items: List<ProblemSet>?){

    (adapter as? SearchResultAdapter)?.run {
        this.submitItems(items)
    }

}

