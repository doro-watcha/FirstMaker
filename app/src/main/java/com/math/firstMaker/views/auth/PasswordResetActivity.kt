package com.math.firstMaker.views.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.widget.Toast
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivityPasswordResetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordResetActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityPasswordResetBinding
    private val mViewModel : PasswordResetViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityPasswordResetBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel


        setContentView(mBinding.root)

        observeViewModel()
    }

    private fun observeViewModel() {

        mViewModel.apply {

            clickChangePassword.observeOnce(this@PasswordResetActivity){
                Toast.makeText(this@PasswordResetActivity,"비밀번호 변경을 완료하였습니다",Toast.LENGTH_SHORT).show()
                finish()
            }

            notSamePassword.observeOnce(this@PasswordResetActivity){
                Toast.makeText(this@PasswordResetActivity,"비밀번호와 확인이 다릅니다",Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {

        fun newIntent ( context : Context) : Intent {

            return Intent(context, PasswordResetActivity::class.java)
        }
    }
}