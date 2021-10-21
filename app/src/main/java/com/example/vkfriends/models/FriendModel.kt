package com.example.vkfriends.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class FriendModel(
    var name: String, var surname: String, var city: String?,
    var avatar: String?, var isOnline: Boolean):Parcelable  {
    override fun describeContents(): Int {
        return 0
    }
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(city.toString())
        parcel.writeString(avatar)
        parcel.writeByte(if (isOnline) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<FriendModel> {
        override fun createFromParcel(parcel: Parcel): FriendModel {
            return FriendModel(parcel)
        }

        override fun newArray(size: Int): Array<FriendModel?> {
            return arrayOfNulls(size)
        }
        fun parse(json: JSONObject)
                = FriendModel(
            name = json.optString("first_name", ""),
            surname = json.optString("last_name", ""),
            city = json?.optJSONObject("city")?.optString("title",""),
            avatar = json.optString("photo_200", ""),
            isOnline = json.optBoolean("online", false))
    }
}