package com.math.firstMaker.views.makeCollection

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.math.firstMaker.args.ProblemListArgs
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivitySetInfoBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class SetInfoActivity : AppCompatActivity() {

    companion object {
        private const val ARG_PROBLEM_LIST = "ARG_PROBLEM_LIST"
        private const val ARG_TYPE = "ARG_TYPE"

        fun newIntent(context: Context, args : ProblemListArgs, type : String) : Intent {
            return Intent(context, SetInfoActivity::class.java).apply{
                putExtra(ARG_PROBLEM_LIST,args)
                putExtra(ARG_TYPE,type)
            }
        }
    }

    /**
     * Binding Instance
     */
    private lateinit var mBinding: ActivitySetInfoBinding

    /**
     * ViewModel Instance
     */
    private lateinit var mViewModel: SetInfoViewModel

    private val args by lazy { intent?.getParcelableExtra(ARG_PROBLEM_LIST) as? ProblemListArgs }
    private val type by lazy { intent?.getStringExtra(ARG_TYPE) }

    override fun onCreate(savedInstanceState: Bundle?) {

        mBinding = ActivitySetInfoBinding.inflate(LayoutInflater.from(this))
        mBinding.lifecycleOwner = this

        mViewModel = getViewModel { parametersOf(args,type) }
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        super.onCreate(savedInstanceState)

        setType()
        observeViewModel()
    }

    private fun observeViewModel() {
        mViewModel.apply {

            publishSuccess.observeOnce(this@SetInfoActivity){
                var type_name = ""
                when ( type ) {
                    "workpaper" -> type_name = "문제지"
                    "workbook" -> type_name = "문제집"
                    "homework" -> type_name = "숙제"
                    "exam" -> type_name = "시험"
                }
                Toast.makeText(this@SetInfoActivity,"${type_name}만들기에 성공하였습니다", Toast.LENGTH_SHORT).show()
                Broadcast.makeCollectionRefresh.onNext(Unit)
                finish()
            }
            errorInvoked.observeOnce(this@SetInfoActivity){

            }

        }

    }

    private fun setType() {
        var type_name = ""
        if ( type == "workpaper") type_name = "문제지"
        else if ( type == "workbook") type_name ="문제집"
        else if ( type == "homework") type_name = "숙제"
        else if ( type == "exam") {
            type_name = "시험"
            mBinding.timeLimitLayout.visibility = View.VISIBLE
        }

        mBinding.makeButton.text = type_name+ " 만들기"
        mBinding.txtSetTitle.text = type_name + " 이름"
        mBinding.editSetTitle.hint = type_name + " 이름을 입력해주세요"

    }

}
