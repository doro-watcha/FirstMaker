package com.math.firstMaker.views.giveCollection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivityGiveCollectionBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.model.ShareRequest
import com.math.firstMaker.views.home.classList.SearchStudentBindingAdapter
import com.math.firstMaker.views.home.classList.StudentBindingAdapter
import com.math.firstMaker.views.makeCollection.MakeCollectionFragment3
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class GiveCollectionActivity : AppCompatActivity() {

    private val TAG = GiveCollectionActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding: ActivityGiveCollectionBinding
    private lateinit var mViewModel: GiveCollectionViewModel

    private val studentNameChanged: BehaviorSubject<String> = BehaviorSubject.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityGiveCollectionBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mViewModel = getViewModel {
            parametersOf(
                intent?.getParcelableExtra(ARG_SHARE_REQUEST)
            )
        }
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        initView()
        listenStudentNameChanged()
        observeViewModel()
        setupRecyclerView()
    }

    private fun initView() {


    }

    private fun observeViewModel() {


        mViewModel.apply {

            isNew = intent?.getBooleanExtra(ARG_IS_NEW,false) ?: false


            studentName.observe(this@GiveCollectionActivity){
                studentNameChanged.onNext(it)
            }

            onSaveCollection.observeOnce(this@GiveCollectionActivity){
                Toast.makeText(this@GiveCollectionActivity,"$it 생성을 완료했습니다",Toast.LENGTH_SHORT).show()
            }

            noTimeLimit.observeOnce(this@GiveCollectionActivity){
                Toast.makeText(this@GiveCollectionActivity,"제한 시간을 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            noStudent.observeOnce(this@GiveCollectionActivity){
                Toast.makeText(this@GiveCollectionActivity,"학생을 선택해주세요",Toast.LENGTH_SHORT).show()
            }

            clickConfirm.observeOnce(this@GiveCollectionActivity){
                finish()
            }
        }
    }

    private fun setupRecyclerView() {


        mBinding.searchRecyclerView.apply {

            adapter = SearchStudentBindingAdapter().apply {

                onClickItem.subscribe {
                    mViewModel.students.apply {
                        if (this.value?.size == 0) mViewModel.students.value = listOf(it)
                        else {
                            this.value = this.value?.plus(listOf(it))?.distinctBy { it.id }
                        }
                    }
                    debugE(TAG, mViewModel.students.value)
                    mViewModel.searchStudents.value = null
                }.disposedBy(compositeDisposable)
            }
        }

        mBinding.studentRecyclerView.apply {

            adapter = StudentBindingAdapter().apply {

                onRemoveEvent.subscribe {
                    mViewModel.students.value =
                        mViewModel.students.value?.minus(listOf(it))?.distinctBy { it.id }
                }.disposedBy(compositeDisposable)
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

    companion object {

        private const val ARG_SHARE_REQUEST = "ARG_SHARE_REQUEST"
        private const val ARG_IS_NEW = "ARG_IS_NEW"

        fun newIntent(context: Context, shareRequest: ShareRequest, isNew : Boolean? = null ): Intent {

            val intent = Intent(context, GiveCollectionActivity::class.java)
            intent.putExtra(ARG_SHARE_REQUEST, shareRequest)
            intent.putExtra(ARG_IS_NEW, isNew)

            return intent
        }
    }
}