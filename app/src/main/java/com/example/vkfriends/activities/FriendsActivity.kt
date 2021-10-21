package com.example.vkfriends.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.vkfriends.R
import com.example.vkfriends.adapters.FriendAdapter
import com.example.vkfriends.models.FriendModel
import com.example.vkfriends.presenters.FriendsPresenter
import com.example.vkfriends.views.FriendsView
import com.github.rahatarmanahmed.cpv.CircularProgressView

class FriendsActivity : MvpAppCompatActivity(), FriendsView {

    private lateinit var mAdapter: FriendAdapter
    private lateinit var mRvFriends: RecyclerView
    private lateinit var mTxtNoItems:TextView
    private lateinit var mCpvWait:CircularProgressView

    @InjectPresenter
    lateinit var friendsPresenter:FriendsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        mRvFriends = findViewById(R.id.recycler_friends)
        mTxtNoItems = findViewById(R.id.txt_friends_no_items)
        mCpvWait = findViewById(R.id.cpv_friends)
        val mTxtSearch: EditText = findViewById(R.id.txt_friends_search)
        mTxtSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        friendsPresenter.loadFriends()
        mAdapter = FriendAdapter()

        mRvFriends.adapter = mAdapter
        mRvFriends.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
        mRvFriends.setHasFixedSize(true)


    }

    //Friends View implementation

    override fun showError(textResource: Int) {
        mTxtNoItems.text = getString(textResource)
    }

    override fun setupEmptyList() {
        mRvFriends.visibility = View.GONE
        mTxtNoItems.visibility = View.VISIBLE
    }



    override fun setupFriendsList(friendsList: List<FriendModel>) {
        mRvFriends.visibility = View.VISIBLE
        mTxtNoItems.visibility = View.GONE

        mAdapter.setupFriends(friendList = friendsList)
    }

    override fun startLoading() {
        mRvFriends.visibility = View.GONE
        mTxtNoItems.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mCpvWait.visibility = View.GONE
    }
}