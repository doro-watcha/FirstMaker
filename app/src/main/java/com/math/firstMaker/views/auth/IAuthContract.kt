package com.math.firstMaker.views.auth

import com.math.firstMaker.base.presenter.IBasePresenter
import com.math.firstMaker.base.presenter.IBaseView


interface IAuthContract {
    interface View : IBaseView {
        fun onAuthCompleted( token:String? )
        fun onAuthFailed(e : String)

    }

    interface Presenter : IBasePresenter<View> {
        fun login(email:String, password:String)
        fun signup(  userId: String, password : String, password2: String, username : String , school : String, admissionYear : String, mathGrade : String)
    }
}