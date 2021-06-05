package com.math.firstMaker.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.math.firstMaker.common.AutoClearedValue
import com.math.firstMaker.databinding.DialogTextSingleBinding
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt


/**
 * created By DORO 2020/11/11
 */

fun AppCompatActivity.showTextDialog(
    titleResource: Int,
    bodyResource : Int
) {
    TextSingleDialog.show(
        supportFragmentManager,titleResource, bodyResource
    )
}

fun Fragment.showTextDialog(
    titleResource: Int,
    bodyResource : Int
) {
    TextSingleDialog.show(
        childFragmentManager, titleResource, bodyResource
    )
}



class TextSingleDialog(
    private val titleResource : Int,
    private val bodyResource : Int

): DialogFragment() {

    companion object {
        fun show(
            fm: FragmentManager,
            titleResource: Int,
            bodyResource: Int) {
            val dialog = TextSingleDialog(titleResource, bodyResource)
            dialog.show(fm, dialog.tag)
        }
    }


    private val TAG = TextSingleDialog::class.java.simpleName

    /**
     * Binding Instance
     */
    private var mBinding: DialogTextSingleBinding by AutoClearedValue()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogTextSingleBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.lifecycleOwner = viewLifecycleOwner

        initView()
    }

    private fun initView () {
        mBinding.apply {

            title.text = (resources.getString(titleResource))
            body.text = (resources.getString(bodyResource))

            txtConfirm.setOnClickListener{
                dismiss()
            }

        }
    }

    /**
     * For Size
     */
    override fun onResume() {
        super.onResume()

        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        val width = (point.x * 0.9f).roundToInt()
        val height = (point.y * 0.3f).roundToInt()
        dialog?.window?.setLayout(width, height)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }


}