package com.example.vkfriends.presenters

import android.content.Intent
import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vkfriends.R
import com.example.vkfriends.views.LoginView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback


@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {
    fun login(isSuccess: Boolean) {
        viewState.startLoading()
        Handler().postDelayed({
            viewState.endLoading()
            if (isSuccess) viewState.openFriends()
            else viewState.showError(textResource = R.string.login_error_credentials)
        }, 500)
    }

    fun loginVK(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (!VK.onActivityResult(requestCode = requestCode, resultCode = resultCode, data = data, callback = object : VKAuthCallback {
                override fun onLogin(token: VKAccessToken) {
                    viewState.openFriends()
                }

                override fun onLoginFailed(errorCode: Int) {
                    viewState.showError(textResource = R.string.login_error_credentials)
                }
            })){
            return false
        }
        return true
    }

}
