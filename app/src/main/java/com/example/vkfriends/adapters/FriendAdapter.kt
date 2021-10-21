package com.example.vkfriends.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vkfriends.R
import com.example.vkfriends.models.FriendModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mSourceList: ArrayList<FriendModel> = ArrayList()
    private var mFriendsList: ArrayList<FriendModel> = ArrayList()

    fun filter(query: String) {
        mFriendsList.clear()
        mSourceList.forEach {
            if (it.name.contains(query, ignoreCase = true) || it.surname.contains(
                    query,
                    ignoreCase = true
                )
            ) {
                mFriendsList.add(it)
            } else {
                it.city?.let { city ->
                    if (city.contains(query, ignoreCase = true)) {
                        mFriendsList.add(it)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }


    fun setupFriends(friendList: List<FriendModel>){
        mSourceList.clear()
        mSourceList.addAll(friendList)
        filter(query = "")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val itemView = layoutInflater.inflate(R.layout.cell_friend,p0,false)
        return FriendViewHolder(itemView = itemView)

    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is FriendViewHolder){
            p0.bind(friendModel = mFriendsList[p1])
        }
    }

    override fun getItemCount(): Int {
        return mFriendsList.count()
    }


    class FriendViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private var mCivAvatar: CircleImageView = itemView.findViewById(R.id.friend_civ_avatar)
        private var mTxtUsername: TextView = itemView.findViewById(R.id.friend_txt_name)
        private var mTxtCity: TextView = itemView.findViewById(R.id.friend_txt_city)
        private var mImgOnline: View = itemView.findViewById(R.id.friend_img_online)

        fun bind(friendModel: FriendModel){
            friendModel?.avatar.let { url -> Picasso.with(itemView.context).load(friendModel.avatar)
                .into(mCivAvatar) }

            mTxtUsername.text = "${friendModel.name} ${friendModel.surname}"
            mTxtCity.text = itemView.context.getString(R.string.friend_no_city)
            friendModel.city?.let { mTxtCity.text = it }

            if(friendModel.isOnline)
                mImgOnline.visibility = View.VISIBLE
            else mImgOnline.visibility = View.GONE
        }
    }
}