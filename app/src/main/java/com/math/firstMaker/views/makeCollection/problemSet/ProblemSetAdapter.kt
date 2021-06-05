package com.math.firstMaker.views.makeCollection.problemSet

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
import com.math.firstMaker.model.Problem
import com.math.firstMaker.model.Publish
import com.math.firstMaker.views.makeCollection.MakeCollectionViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 1/14/21
 */


class ProblemSetAdapter : RecyclerView.Adapter<ProblemSetAdapter.ProblemBindingHolder>(){

    private val onReplace: PublishSubject<Problem> = PublishSubject.create()
    val onReplaceEvent : Observable<Problem> = onReplace

    private val onBlackList : PublishSubject<Problem> = PublishSubject.create()
    val onBlackListEvent : Observable<Problem> = onBlackList


    private val diff = object : DiffUtil.ItemCallback<Problem>() {
        override fun areItemsTheSame(oldItem: Problem, newItem: Problem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Problem, newItem: Problem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<Problem>(this,diff)


    fun submitItems( items: List<Problem>? ){
        differ.submitList(items)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProblemSetBinding.inflate(inflater, parent, false)

        return ProblemBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ProblemBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class ProblemBindingHolder( private val binding: ItemProblemSetBinding) : RecyclerView.ViewHolder(binding.root){

        init{

            binding.btnReplace.setOnDebounceClickListener {
                onReplace.onNext(differ.currentList[layoutPosition])
            }

            binding.btnBlackList.setOnDebounceClickListener {
                onBlackList.onNext(differ.currentList[layoutPosition])
            }
        }

        fun bind ( item : Problem) {
            binding.setVariable(BR.item , item)
            binding.executePendingBindings()

//            binding.problemIndex.text = item.id.toString() + "번 문제"
//            binding.course.text = "과목 : " + item.course
//            binding.bigChapter.text = "대단원 : " + item.bigChapter
//            binding.middleChapter.text = "중단원 : " + item.middleChapter
//            binding.smallChapter.text = "소단원 : " + item.smallChapter
//            binding.source.text = "출처 : " + item.source
//            binding.level.text = "난이도 : " + item.level.toString()


        }
    }
}

@BindingAdapter("app:recyclerview_problem_set")
fun RecyclerView.setProblemSet(items: List<Problem>?){

    (adapter as? ProblemSetAdapter)?.run {
        this.submitItems(items)
    }

}
