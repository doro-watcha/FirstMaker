package com.math.firstMaker.views.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.math.firstMaker.R
import com.math.firstMaker.api.UnWrappingDataException
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivityLoginBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.dialog.showTextDialog
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.views.auth.signUp.SignUpActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {


    /**
     * Binding Instance
     */
    private var _mBinding: ActivityLoginBinding? = null
    private val mBinding: ActivityLoginBinding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel : LoginViewModel by viewModel()

    private val navigator : Navigator by inject()


    override fun onCreate(savedInstanceState: Bundle?) {

        _mBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this

        mBinding.vm = mViewModel
        setContentView(mBinding.root)

        super.onCreate(savedInstanceState)

        observeViewModel()

    }

    private fun observeViewModel () {
        mViewModel.apply{


            loginSuccess.observeOnce(this@LoginActivity) {
                navigator.startMainActivity(this@LoginActivity)

                finish()
            }

            errorInvoked.observeOnce(this@LoginActivity) {
                debugE(TAG, it.message)
                if (it is UnWrappingDataException) {
                    when (it.errorCode) {

                        // 비밀번호 패턴 안맞음
                        102 -> showTextDialog(
                            titleResource = R.string.common_login_error,
                            bodyResource = R.string.error_password_constraint_txt
                        )
                        // 비밀번호 안맞음
                        200 -> showTextDialog(
                            titleResource = R.string.common_login_error,
                            bodyResource = R.string.error_wrong_password_txt
                        )
                        // 존재하지 않는 유저
                        402 -> showTextDialog(
                            titleResource = R.string.common_login_error,
                            bodyResource = R.string.error_user_not_found
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

            goSignUp.observeOnce(this@LoginActivity) {
//                navigator.startSignUpActivity(
//                    this@LoginActivity,
//                    R.anim.slide_in_from_right,
//                    R.anim.slide_out_to_left
//                )
                val intent = SignUpActivity.newIntent(this@LoginActivity)
                startActivity(intent)
            }

            clickResetPassword.observeOnce(this@LoginActivity){
                navigator.startResetPasswordActivity(this@LoginActivity)
            }
        }


    }
    /**
     * Class Factory
     */
    companion object {
        private val TAG: String = LoginActivity::class.java.simpleName

        private val INTENT_PARAM_USER_ID: String? = "userId"
        private val INTENT_PARAM_USER_PWD: String? = "password"

        fun newIntent(context:Context, userId:String? = null, userPwd:String? = null): Intent {
            return Intent(context, LoginActivity::class.java).apply {
                userId?.let { putExtra(INTENT_PARAM_USER_ID, it) }
                userPwd?.let { putExtra(INTENT_PARAM_USER_PWD, it) }
            }
        }
    }
}


