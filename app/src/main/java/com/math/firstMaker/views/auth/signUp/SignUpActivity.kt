package com.math.firstMaker.views.auth.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.math.firstMaker.R
import com.math.firstMaker.api.UnWrappingDataException
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivitySignUpBinding
import com.math.firstMaker.dialog.showTextDialog
import com.math.firstMaker.navigation.Navigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {
    private val TAG: String = "SignUpActivity"


    /**
     * Binding Instance
     */
    private var _mBinding: ActivitySignUpBinding? = null
    private val mBinding: ActivitySignUpBinding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel : SignUpViewModel by viewModel()

    private val navigator : Navigator by inject()


    override fun onCreate(savedInstanceState: Bundle?) {

        _mBinding = ActivitySignUpBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this

        mBinding.vm = mViewModel
        setContentView(mBinding.root)

        super.onCreate(savedInstanceState)

        observeViewModel()
        setupViewPager()

    }

    private fun setupViewPager() {

        mBinding.signUpViewPager.apply {

            isUserInputEnabled = false

            adapter = SignUpTabAdapter(supportFragmentManager, 2)
        }

    }

    private fun observeViewModel () {


        mViewModel.apply {

            navigatePage.observe(this@SignUpActivity){
                mBinding.signUpViewPager.setCurrentItem(it, false)
            }

            signUpSuccess.observeOnce(this@SignUpActivity){
                Toast.makeText(this@SignUpActivity,"회원가입에 성공하였습니다",Toast.LENGTH_SHORT).show()
                finish()
            }

            clickFinish.observeOnce(this@SignUpActivity){
                finish()
            }

            errorInvoked.observeOnce(this@SignUpActivity){

                if (it is UnWrappingDataException) {
                    when (it.errorCode) {
                        102 -> showTextDialog(
                            titleResource = R.string.common_sign_up_error,
                            bodyResource = R.string.error_content_not_filled_txt
                        )
                        200 -> showTextDialog(
                            titleResource = R.string.common_sign_up_error,
                            bodyResource = R.string.error_wrong_password_txt
                        )
                        500 -> showTextDialog(
                            titleResource = R.string.common_sign_up_error,
                            bodyResource = R.string.error_user_already_exists
                        )
                        430 -> showTextDialog(
                            titleResource = R.string.common_sign_up_error,
                            bodyResource = R.string.error_wrong_teacher_code
                        )
                        else -> showTextDialog(
                            titleResource = R.string.common_error,
                            bodyResource = R.string.error_common_txt
                        )
                    }
                } else {
                    showTextDialog(titleResource = R.string.common_error, bodyResource = R.string.error_common_txt)
                }
            }


        }
    }

    inner class SignUpTabAdapter(fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount


        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StudentFragment.newInstance()
                else -> TeacherFragment.newInstance()
            }
        }

    }

    /**
     * Class Factory
     */
    companion object {
        private val TAG: String = SignUpActivity::class.java.simpleName

        fun newIntent(context:Context):Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}
