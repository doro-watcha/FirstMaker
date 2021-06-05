package com.math.firstMaker.navigation

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.math.firstMaker.MainActivity
import com.math.firstMaker.R
import com.math.firstMaker.args.ProblemListArgs
import com.math.firstMaker.debugE
import com.math.firstMaker.model.Problem
import com.math.firstMaker.model.ProblemSet
import com.math.firstMaker.model.ShareRequest
import com.math.firstMaker.views.IMainMenu
import com.math.firstMaker.views.MainMenu.Companion.parseIndexToMainMenu
import com.math.firstMaker.views.auth.PasswordResetActivity
import com.math.firstMaker.views.auth.signUp.SignUpActivity
import com.math.firstMaker.views.giveCollection.GiveCollectionActivity
import com.math.firstMaker.views.home.classList.MakeClassActivity
import com.math.firstMaker.views.home.classList.detail.ClassDetailActivity
import com.math.firstMaker.views.home.detail.NoteDetailActivity
import com.math.firstMaker.views.makeCollection.SetInfoActivity
import com.math.firstMaker.views.makeCollection.problemSet.ProblemSetActivity
import com.math.firstMaker.views.publishPage.PublishPageActivity
import com.math.firstMaker.views.result.ResultActivity
import com.math.firstMaker.views.review.ReviewProblemActivity
import com.math.firstMaker.views.workBook.add.AddWorkBookActivity

class NavigatorImpl : Navigator {

    private val TAG = NavigatorImpl::class.java.simpleName

    override val curMainMenu: MutableLiveData<IMainMenu> = MutableLiveData(parseIndexToMainMenu(0))


    private fun pop(activity: Activity) {
        activity.finish()
        activity.overridePendingTransition(R.anim.transition_pop_enter, R.anim.transition_pop_exit)
    }



    private fun push(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.transition_enter, R.anim.transition_exit)
    }

    private fun push(activity: Activity, intent: Intent, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
        activity.overridePendingTransition(R.anim.transition_enter, R.anim.transition_exit)
    }

    private fun bottomUp ( activity : Activity , intent : Intent) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.from_slide_bottom_up, R.anim.fade_out)
    }

    private fun topDown ( activity : Activity , intent : Intent) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.from_slide_top_down, R.anim.fade_out)
    }

    private fun slide( activity : Activity , intent : Intent) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
    }

    override fun startMainActivity(activity: Activity) {
        val intent = MainActivity.newIntent(activity)
        activity.startActivity(intent)
    }


    override fun startSignUpActivity(activity: Activity, enterAnim: Int, exitAnim: Int) {
        val intent = SignUpActivity.newIntent(activity)
        slide(activity,intent)
    }


    override fun startSetInfoActivity(
        activity: Activity,
        problems: List<Problem>,
        type: String,
        enterAnim: Int,
        exitAnim: Int
    ) {
        val intent = SetInfoActivity.newIntent(activity,
            ProblemListArgs(
                problems = problems
            ),
            type
        )
        activity.startActivity(intent)
        activity.overridePendingTransition(enterAnim,exitAnim)
    }


    override fun startPublishPageActivity(
        activity: Activity,
        type : String,
        id : Int
    ) {
        val intent = PublishPageActivity.newIntent(activity,type ,id)
        activity.startActivity(intent)
    }

    override fun startResultActivity(activity: Activity, publishId: Int) {
        val intent = ResultActivity.newIntent(
            context = activity,
            publishId = publishId
        )
        activity.startActivity(intent)
    }
    override fun startReviewActivity(
        activity: Activity,
        problems: List<Problem>,
        editMode : Boolean,
        enterAnim: Int,
        exitAnim: Int
    ) {
        val intent = ReviewProblemActivity.newIntent(
            activity,
            ProblemListArgs(
                problems = problems
            ),
            editMode
        )
        activity.startActivityForResult(intent,Navigator.REQUEST_REVIEW_PROBLEM_CODE)
    }

    override fun startStudentManageActivity(activity: Activity, studentId: Int) {

    }

    override fun startClassDetailActivity(activity: Activity, classId: Int) {
        val intent = ClassDetailActivity.newIntent(activity,classId)
        activity.startActivity(intent)
    }

    override fun startNoteListActivity(activity: Activity, collectionId: Int , type : String, position : Int ) {

        val intent = NoteDetailActivity.newIntent(activity, collectionId, type , position )
        activity.startActivity(intent)
    }

    override fun startProblemSetActivity(activity: Activity, problemSet: ProblemSet, minLevel : Int, maxLevel : Int ) {

        debugE(TAG,problemSet)

        val intent = ProblemSetActivity.newIntent(activity,problemSet, minLevel , maxLevel )
        activity.startActivity(intent)
    }

    override fun startGiveCollectionActivity(activity: Activity, shareRequest: ShareRequest, isNew : Boolean?) {

        val intent = GiveCollectionActivity.newIntent(activity, shareRequest, isNew)
        slide(activity,intent)
    }

    override fun startResetPasswordActivity(activity: Activity) {

        val intent = PasswordResetActivity.newIntent(activity)
        slide(activity,intent)
    }

    override fun startAddClassActivity(activity: Activity) {
        val intent = MakeClassActivity.newIntent(activity)
        slide(activity,intent)
    }

    override fun startAddWorkBookActivity(activity: Activity) {
        val intent = AddWorkBookActivity.newIntent(activity)
        activity.startActivity(intent)
    }

}