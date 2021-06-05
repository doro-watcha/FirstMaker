package com.math.firstMaker.views.home.classList

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.observe
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivityMakeClassBinding
import com.math.firstMaker.debugE
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MakeClassActivity : AppCompatActivity() {

    private val TAG = MakeClassActivity::class.java.simpleName

    private lateinit var mBinding : ActivityMakeClassBinding
    private val mViewModel : MakeClassViewModel by viewModel()

    private val compositeDisposable = CompositeDisposable()

    private val studentNameChanged : BehaviorSubject<String> = BehaviorSubject.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMakeClassBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        listenStudentNameChanged()
        setupRecyclerView()
        observeViewModel()
    }


    private fun observeViewModel () {

        mViewModel.apply {

            studentName.observe(this@MakeClassActivity){
                studentNameChanged.onNext(it)
            }

            needClassName.observeOnce(this@MakeClassActivity){
                Toast.makeText(this@MakeClassActivity,"클래스 이름을 입력해주세요",Toast.LENGTH_SHORT).show()
            }

            classCreateCompleted.observeOnce(this@MakeClassActivity){
                Toast.makeText(this@MakeClassActivity,"${className.value} 클래스를 생성하였습니다", Toast.LENGTH_SHORT).show()
            }

            errorInvoked.observeOnce(this@MakeClassActivity){
                debugE(TAG,it)
            }

        }
    }


    private fun listenStudentNameChanged() {


        studentNameChanged
            .debounce(300L, TimeUnit.MILLISECONDS)
            .addSchedulers()
            .subscribe {
                mViewModel.listUsersByStudentName(it)
            }.disposedBy(compositeDisposable)
    }

    private fun setupRecyclerView() {

        mBinding.searchRecyclerView.apply {

            adapter = SearchStudentBindingAdapter().apply{

                onClickItem.subscribe{
                    mViewModel.students.apply {
                        if ( this.value?.size == 0 ) mViewModel.students.value = listOf(it)
                        else {
                            this.value = this.value?.plus(listOf(it))?.distinctBy { it.id }
                        }
                    }
                    debugE(TAG, mViewModel.students.value)
                    mViewModel.searchStudents.value = null
                }.disposedBy(compositeDisposable)
            }
        }

        mBinding.mRecyclerView.apply {

            adapter = StudentBindingAdapter().apply{

                onRemoveEvent.subscribe{
                    mViewModel.students.value = mViewModel.students.value?.minus(listOf(it))?.distinctBy { it.id }
                }.disposedBy(compositeDisposable)
            }
        }


    }

    companion object {

        fun newIntent( context : Context) : Intent {
            return Intent(context, MakeClassActivity::class.java)
        }
    }
}