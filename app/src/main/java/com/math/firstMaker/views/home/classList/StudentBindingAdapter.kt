package com.math.firstMaker.views.home.classList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.databinding.ItemStudentBinding
import com.math.firstMaker.model.User
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 1/3/21
 */

class StudentBindingAdapter : RecyclerView.Adapter<StudentBindingAdapter.StudentBindingHolder>(){

    private val onRemove: PublishSubject<User> = PublishSubject.create()
    val onRemoveEvent : Observable<User> = onRemove

    private val diff = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<User>(this,diff)


    fun submitItems( items: List<User>? ){
        differ.submitList(items)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStudentBinding.inflate(inflater, parent, false)

        return StudentBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: StudentBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class StudentBindingHolder( private val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.btnRemove.setOnClickListener{
                onRemove.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind ( item : User) {
            binding.setVariable(BR.item , item)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_students_list")
fun RecyclerView.setStudent(items: List<User>?){

    (adapter as? StudentBindingAdapter)?.run {
        this.submitItems(items)
    }
}
