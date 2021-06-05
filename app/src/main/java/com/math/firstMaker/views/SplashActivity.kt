package com.math.firstMaker.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.math.firstMaker.MainActivity
import com.math.firstMaker.databinding.ActivitySplashBinding
import com.math.firstMaker.R
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.debugE
import com.math.firstMaker.dialog.showTextDialog
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.views.auth.LoginActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : AppCompatActivity(){

    /**
     * Binding Instance
     */

    private lateinit var mBinding : ActivitySplashBinding


    /**
     *  ViewModel Instance
     */

    private val mViewModel : SplashViewModel by viewModel()

    private val authRepository : AuthRepository by inject()


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        mBinding = ActivitySplashBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        debugE(TAG, "ONCREATE")

        mViewModel.waitSplashScreen()

        observeViewModel()
    }

    private fun observeViewModel() {

        mViewModel.apply {

            screenOver.observeOnce(this@SplashActivity){

                if(authRepository.curUser.value == null) {
                    val intent = LoginActivity.newIntent(this@SplashActivity)
                    startActivity(intent)
                    finish()
                }else {
                    val intent = MainActivity.newIntent(this@SplashActivity)
                    startActivity(intent)
                    finish()
                }
            }

            errorInvoked.observeOnce(this@SplashActivity){
                showTextDialog(
                    titleResource = R.string.common_error,
                    bodyResource = R.string.error_network_txt
                )

            }
        }
    }




    companion object {
        private val TAG: String = SplashActivity::class.java.simpleName

        fun newIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }
}
