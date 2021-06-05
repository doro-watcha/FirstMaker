package com.math.firstMaker.views.home.classList.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.widget.bottomDialog.setOnDebounceClickListener
import com.math.firstMaker.databinding.ItemMyStudentBinding
import com.math.firstMaker.databinding.ItemStudentBinding
import com.math.firstMaker.model.Publish
import com.math.firstMaker.model.Student
import com.math.firstMaker.views.home.classList.StudentBindingAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 1/3/21
 */


class MyStudentAdapter : RecyclerView.Adapter<MyStudentAdapter.StudentBindingHolder>(){

    private val onClick : PublishSubject<Student> = PublishSubject.create()
    val onClickEvent : Observable<Student> = onClick

    private val onRemove: PublishSubject<Student> = PublishSubject.create()
    val onRemoveEvent : Observable<Student> = onRemove

    private val diff = object : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer<Student>(this,diff)


    fun submitItems( items: List<Student>? ){
        differ.submitList(items)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentBindingHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMyStudentBinding.inflate(inflater, parent, false)

        return StudentBindingHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: StudentBindingHolder, position: Int) = holder.bind(differ.currentList[position])


    inner class StudentBindingHolder( private val binding: ItemMyStudentBinding) : RecyclerView.ViewHolder(binding.root){

        init{

            binding.delete.setOnDebounceClickListener{
                onRemove.onNext(differ.currentList[layoutPosition])
            }

            binding.root.setOnDebounceClickListener{
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind ( item : Student) {
            binding.setVariable(BR.item , item)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_my_students_list")
fun RecyclerView.setMyStudents(items: List<Student>?){

    (adapter as? MyStudentAdapter)?.run {
        this.submitItems(items)
    }
}
