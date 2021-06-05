package com.math.firstMaker.navigation

import android.app.Activity
import androidx.annotation.AnimRes
import androidx.lifecycle.MutableLiveData
import com.math.firstMaker.model.Note
import com.math.firstMaker.model.Problem
import com.math.firstMaker.model.ProblemSet
import com.math.firstMaker.model.ShareRequest
import com.math.firstMaker.views.IMainMenu

interface Navigator {

    val curMainMenu: MutableLiveData<IMainMenu>

    companion object {
        const val RESULT_REVIEW_PROBLEM_CODE = 1001
        const val REQUEST_REVIEW_PROBLEM_CODE = 1000
    }

    fun startMainActivity (
        activity : Activity
    )

    fun startSignUpActivity(
        activity : Activity , @AnimRes enterAnim: Int = 0 , @AnimRes exitAnim : Int = 0
    )

    fun startSetInfoActivity (
        activity : Activity , problems : List<Problem> , type : String , @AnimRes enterAnim: Int = 0, @AnimRes exitAnim: Int = 0
    )

    fun startPublishPageActivity(
        activity : Activity,
        type : String,
        id : Int
    )

    fun startResultActivity(
        activity : Activity, publishId : Int
    )


    fun startReviewActivity (
        activity : Activity , problems : List<Problem> , editMode : Boolean , @AnimRes enterAnim : Int = 0 , @AnimRes exitAnim : Int = 0
    )

    fun startStudentManageActivity(
        activity : Activity, studentId: Int
    )

    fun startClassDetailActivity (
        activity :Activity, classId : Int
    )

    fun startNoteListActivity (
        activity : Activity, collectionId : Int, type : String, position : Int
    )

    fun startProblemSetActivity(
        activity : Activity , problemSet : ProblemSet, minLevel: Int , maxLevel : Int
    )

    fun startGiveCollectionActivity(
        activity : Activity , shareRequest: ShareRequest, isNew : Boolean? = null
    )

    fun startResetPasswordActivity(
        activity : Activity
    )

    fun startAddClassActivity(
        activity : Activity
    )

    fun startAddWorkBookActivity(
        activity : Activity
    )
}
