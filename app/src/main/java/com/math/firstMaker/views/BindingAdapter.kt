package com.math.firstMaker.views

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.math.firstMaker.R
import com.math.firstMaker.model.Note
import com.math.firstMaker.model.Problem
import com.math.firstMaker.debugE
import com.math.firstMaker.views.publishPage.PublishPageBindingAdapter
import com.math.firstMaker.views.result.ResultBindingAdapter

@BindingAdapter("app:imageSrc")
fun ImageView.setImageSrcGlide(src: String?) {

    val localURL = "http://172.30.1.1:3000" + src
    val serverURL = "http://ec2-3-132-216-124.us-east-2.compute.amazonaws.com" + src
    debugE("SRC", serverURL)
    Glide.with(this).load(serverURL).transition(DrawableTransitionOptions.withCrossFade()).into(this)
}



@BindingAdapter("app:viewpager2_problem_items")
fun ViewPager2.setProblem(items: List<Problem>) {
    (adapter as? PublishPageBindingAdapter)?.run {
        this.submitItems(items)
    }
}

@BindingAdapter("app:recyclerview_result_items")
fun RecyclerView.setProblem(items: List<Note>) {
    (adapter as? ResultBindingAdapter)?.run {
        this.submitItems(items)
    }
}

@BindingAdapter("selectedValue")
fun Spinner.setSelectedValue(selectedValue: Any?) {
    setSpinnerValue(selectedValue)
}

@BindingAdapter("selectedValueAttrChanged")
fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
    setSpinnerInverseBindingListener(inverseBindingListener)
}

object InverseSpinnerBindings {

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    fun Spinner.getSelectedValue(): Any? {
        return getSpinnerValue()
    }
}

fun Spinner.setSpinnerItemSelectedListener(listener: ItemSelectedListener?) {
    if (listener == null) {
        onItemSelectedListener = null
    } else {
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (tag != position) {
                    listener.onItemSelected(parent.getItemAtPosition(position))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}

fun Spinner.setSpinnerInverseBindingListener(listener: InverseBindingListener?) {
    if (listener == null) {
        onItemSelectedListener = null
    } else {
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (tag != position) {
                    listener.onChange()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
}

fun Spinner.setSpinnerValue(value: Any?) : Any?{
    return selectedItem
}

fun Spinner.getSpinnerValue(): Any? {
    return selectedItem
}

interface ItemSelectedListener {
    fun onItemSelected(item: Any?)
}

@BindingAdapter("android:visibility_invisible")
fun View.setVisibilityInvisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("app:problem_text_color")
fun TextView.setProblemBackgroundColor ( note : Note ) {
    var textColor = 0
    if ( note.status == "맞음"){
        textColor = R.color.blue
    }else if ( note.status =="틀림") {
        textColor = R.color.colorRed
    } else if ( note.submit.get() != "0" ) {
        textColor = R.color.default_main_color
    } else {
        textColor = R.color.default_black
    }

    this.setTextColor(resources.getColor(textColor))
}



