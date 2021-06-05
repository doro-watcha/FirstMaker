package com.math.firstMaker.views.home.classList.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivityClassDetailBinding
import com.math.firstMaker.debugE
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class ClassDetailActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityClassDetailBinding
    private lateinit var  mViewModel : ClassDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityClassDetailBinding.inflate(LayoutInflater.from(this))

        mViewModel = getViewModel {
            parametersOf(intent.getIntExtra(ARG_CLASS_ID,0))
        }

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        observeViewModel()
        setupViewPager()
    }

    private fun setupViewPager() {
        mBinding.mViewPager.apply {
            isUserInputEnabled = false
            adapter = ClassDetailTabAdapter(supportFragmentManager,3)
        }

    }

    private fun observeViewModel() {

        mViewModel.apply {

            navigatePage.observe(this@ClassDetailActivity){
                mBinding.mViewPager.currentItem = it
            }

            addStudentCompleted.observeOnce(this@ClassDetailActivity){
                Toast.makeText(this@ClassDetailActivity,"${curStudent.value?.name} 학생을 클래스에 추가하였습니다", Toast.LENGTH_SHORT).show()
                curStudent.value = null
                studentName.value = ""
                getClass()
            }

            onCollectionLoadCompleted.observe(this@ClassDetailActivity){
                if ( it) {
                    navigatePage.value = 1
                }
            }

            deleteStudentCompleted.observeOnce(this@ClassDetailActivity){
                getClass()
            }
            errorInvoked.observeOnce(this@ClassDetailActivity){
                debugE(TAG, it)
            }
        }

    }

    override fun onBackPressed() {

        if ( mViewModel.navigatePage.value == 0) super.onBackPressed()
        else {
            mViewModel.navigatePage.value = 0
        }
    }


    inner class ClassDetailTabAdapter(fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount


        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StudentListFragment.newInstance()
                1 -> StudentDetailFragment.newInstance()
                else -> StudentResultFragment.newInstance()
            }
        }

    }

    companion object {
        private val TAG: String = ClassDetailActivity::class.java.simpleName

        private const val ARG_CLASS_ID = "ARG_CLASS_ID"
        fun newIntent(context: Context, classId : Int ): Intent {
            val intent = Intent ( context, ClassDetailActivity::class.java)
            intent.putExtra(ARG_CLASS_ID, classId)
            return intent
        }
    }
}