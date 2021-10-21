package com.example.vkfriends.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.vkfriends.R
import com.example.vkfriends.presenters.LoginPresenter
import com.example.vkfriends.views.LoginView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils

class LoginActivity : MvpAppCompatActivity(),LoginView {
    private lateinit var mCpvWait: CircularProgressView
    private lateinit var mBtnEnter: Button
    private lateinit var mTxtHello: TextView

    @InjectPresenter
    lateinit var loginPresenter:LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mTxtHello = findViewById(R.id.txt_login_hello)
        mBtnEnter = findViewById(R.id.btn_login_enter)
        mCpvWait = findViewById(R.id.cpv_login)

        mBtnEnter.setOnClickListener {
            VK.login(this@LoginActivity, arrayListOf(VKScope.FRIENDS))
        }

       val fingerprints: Array<String?>? = VKUtils.getCertificateFingerprint(this, this.packageName)
        fingerprints?.let { Log.e(TAG,"fingerprints ${fingerprints[0]}") }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!loginPresenter.loginVK(requestCode = requestCode, resultCode = resultCode, data = data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startLoading() {
        mBtnEnter.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mBtnEnter.visibility = View.VISIBLE
        mCpvWait.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()

    }

    override fun openFriends() {
        startActivity(Intent(applicationContext,FriendsActivity::class.java))
    }


}