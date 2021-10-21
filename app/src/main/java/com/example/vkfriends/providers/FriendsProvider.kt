package com.example.vkfriends.providers

import android.os.Handler
import android.util.Log
import com.example.vkfriends.models.FriendModel
import com.example.vkfriends.models.VKFriendsGet

import com.example.vkfriends.presenters.FriendsPresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import kotlin.collections.ArrayList

class FriendsProvider(var presenter: FriendsPresenter) {
    fun testLoadFriends(hasFriends: Boolean){
        Handler().postDelayed({
            val friendsList:  ArrayList<FriendModel> = ArrayList()
            if(hasFriends){
                val friend1 = FriendModel(
                    name = "Иван", surname = "Петров", city = null, avatar =
                "https://upload.wikimedia.org/wikipedia/ru/8/86/%D0%98%D0%B2%D0%B0%D0%BD_%D0%98%D0%B2%D0%B0%D0%BD%D0%BE%D0%B2%D0%B8%D1%87_%D0%9F%D0%B5%D1%82%D1%80%D0%BE%D0%B2_%28%D0%BF%D0%B5%D0%B2%D0%B5%D1%86%29.jpg"
                    , isOnline = true)
                val friend2 = FriendModel(
                    name = "Алексей", surname = "Гладков", city = "Moscow",
                    avatar = "https://sun1-22.userapi.com/s/v1/ig2/GLDtNr3iEmRVY98qxuV_lk97n0U2frQO4DxeaW7npVmO07qecBOJaPG7fHPDCGeZrnHeGVidpKlBBpkQyu-C7cg0.jpg?size=200x200&quality=96&crop=4,29,801,801&ava=1"
                    , isOnline = true)
                val friend3 = FriendModel(
                    name = "Егор", surname = "Сидоров", city = "Tver",
                    avatar = "https://www.arsenaltula.ru/upload/iblock/b22/Sidorov_72.png",
                    isOnline = true)
                friendsList.add(friend1)
                friendsList.add(friend2)
                friendsList.add(friend3)
            }
            presenter.friendsLoaded(friendsList = friendsList)
        },2000)
    }
    fun loadFriends(){
        VK.execute(VKFriendsGet(),object : VKApiCallback<List<FriendModel>>{
            override fun fail(error: Exception) {
                presenter.showError(error)
            }

            override fun success(result: List<FriendModel>) {
                presenter.friendsLoaded(result)
            }

        })
    }
}