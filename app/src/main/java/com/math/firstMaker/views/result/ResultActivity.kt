package com.math.firstMaker.views.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivityResultBinding
import com.math.firstMaker.navigation.Navigator

import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class ResultActivity : AppCompatActivity() {

    /**
     * Binding Instance
     */
    private lateinit var mBinding : ActivityResultBinding

    /**
     *  ViewModel Instance
     */

    private val navigator : Navigator by inject()

    private lateinit var mViewModel : ResultViewModel

    private val publishId by lazy { intent?.getIntExtra(ARG_ID,0)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityResultBinding.inflate(LayoutInflater.from(this))
        mBinding.lifecycleOwner = this

        mViewModel = getViewModel{parametersOf(publishId)}
        mBinding.vm = mViewModel
        setContentView(mBinding.root)

        setupRecyclerView()
        observeViewModel()

    }

    private fun setupRecyclerView() {
        mBinding.mRecyclerView.apply{

            adapter = ResultBindingAdapter(
                mViewModel,
                this@ResultActivity
            ).apply{

            }
        }
    }

    private fun observeViewModel () {

        mViewModel.apply{
            errorInvoked.observeOnce(this@ResultActivity){

            }

        }
    }

    companion object {

        const val ARG_ID = "ARG_ID"

        fun newIntent(context : Context , publishId : Int): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(ARG_ID,publishId)
            }
        }
    }
}
