package com.example.vkfriends.models

import com.vk.api.sdk.VK
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class VKFriendsGet : VKRequest<List<FriendModel>> {
    val TAG_LOG = "TAG_LOG"

    constructor(uids: IntArray = intArrayOf()): super("friends.get") {
        if (uids.isNotEmpty()) {
            addParam("user_ids", uids.joinToString(","))
        }
        addParam("fields", "photo_200,city")
    }

    @Throws(Exception::class)
    override fun parse(r: JSONObject): List<FriendModel> {
        val result: MutableList<FriendModel> = ArrayList()
        val response = r.getJSONObject("response")
        val users = response.getJSONArray("items")
        for (i in 0 until users.length()) {
            result.add(FriendModel.parse(users.getJSONObject(i)))
        }
        super.parse(r)
        return result
    }

    init {
        this.addParam("lang","ru")
        this.addParam("fields", "photo_200,city")
    }
}