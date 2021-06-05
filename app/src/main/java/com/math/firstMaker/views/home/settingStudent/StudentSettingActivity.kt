package com.math.firstMaker.views.home.settingStudent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.math.firstMaker.databinding.ActivityStudentSettingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudentSettingActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityStudentSettingBinding
    private val mViewModel : StudentSettingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityStudentSettingBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)
    }

    private fun observeViewModel() {


    }
}