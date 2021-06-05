package com.math.firstMaker.views.workBook.add

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.math.firstMaker.databinding.ActivityAddWorkBookBinding
import com.math.firstMaker.views.workBook.WorkBookFragment1
import com.math.firstMaker.views.workBook.WorkBookFragment2
import com.math.firstMaker.views.workBook.WorkBookFragment3
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddWorkBookActivity : AppCompatActivity() {


    private lateinit var mBinding : ActivityAddWorkBookBinding
    private val mViewModel : AddWorkBookViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mBinding = ActivityAddWorkBookBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        observeViewModel()
        setupViewPager()

    }

    private fun observeViewModel() {



    }


    private fun setupViewPager() {


        mBinding.mViewPager.apply {

            adapter = AddWorkBookAdapter(supportFragmentManager,2)
        }


    }


    inner class AddWorkBookAdapter(fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount


        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AddWorkBookFragment1.newInstance()
                else -> AddWorkBookFragment2.newInstance()
            }
        }

    }

    companion object {

        fun newIntent ( context : Context) : Intent {
            return Intent ( context , AddWorkBookActivity::class.java)
        }
    }
}