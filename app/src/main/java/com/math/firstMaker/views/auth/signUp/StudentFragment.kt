package com.math.firstMaker.views.auth.signUp
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.math.firstMaker.databinding.FragmentSignUpStudentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 1/2/21
 */

class StudentFragment : Fragment() {

    private lateinit var mBinding : FragmentSignUpStudentBinding
    private val mViewModel : SignUpViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSignUpStudentBinding.inflate(inflater, container, false ).also { mBinding = it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner


    }

    companion object {

        fun newInstance () = StudentFragment()
    }


}