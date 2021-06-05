package com.math.firstMaker.views.makeCollection.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.databinding.ItemSubjectBinding
import com.math.firstMaker.model.Subject
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 2020-03-22
 */

class SubjectAdapter : RecyclerView.Adapter<SubjectAdapter.SubjectHolder>(){

    private val onPick: PublishSubject<Subject> = PublishSubject.create()
    val onPickEvent : Observable<Subject> = onPick

    private val diff = object : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Subject>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubjectBinding.inflate(inflater, parent, false)

        return SubjectHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: SubjectHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class SubjectHolder( private val binding: ItemSubjectBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.root.setOnClickListener {
                onPick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind ( item : Subject ) {
            binding.setVariable(BR.item , item)
            binding.executePendingBindings()


        }
    }
}



@BindingAdapter("app:recyclerview_subject_list")
fun RecyclerView.setSubjects(items : List<Subject>?){
    (adapter as? SubjectAdapter)?.run {
        this.submitItems(items)
    }
}
