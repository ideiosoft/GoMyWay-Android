package ssc.snow.app.gomyways.views.home.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_item_inbox.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.inbox.ModelInboxList
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.views.home.controler.ScreenInboxDetail

class AdapterInbox(private val context: Activity, private val listData: List<ModelInboxList.Data?>) : RecyclerView.Adapter<AdapterInbox.MyViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_item_inbox, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        listData.get(i)!!.apply {
            holder.apply {

                mName.text = first_name + " " + last_name
                mlastMsg.text = last_message
                mTime.text = time

                updateMessageCount(new_messages, mUnreadCount)

                // load image of user
                updateWithUrl(profile_image)

                mRootLayout.setOnClickListener {

                    new_messages = 0
                    notifyDataSetChanged()

                    InjectorUtil.mConversationId = conversation_id.toString()
                    InjectorUtil.mToId = from_user_id.toString()

                    // send the control to the next screen for details
                    context.startActivity(Intent(context, ScreenInboxDetail::class.java))
                }
            }        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mImgDriver = view.img_user
        val mName = view.txt_name
        val mUnreadCount = view.textView_unread_count
        val mlastMsg = view.txt_grp_description
        val mTime = view.txt_time
        val mRootLayout = view.rel_root_

        fun updateWithUrl(url: String?) {
            // load image from network in case first laoding and secod time from cache
            Glide.with(itemView.context)
                    .load(url)
                    .into(mImgDriver)
        }

        fun updateMessageCount(newMessages: Int?, mText: TextView) {

            if (newMessages!! == 0) {
                mText.visibility = View.INVISIBLE
            } else if (newMessages < 99) {
                mText.visibility = View.VISIBLE
                mText.text = newMessages.toString()
            } else {
                mText.visibility = View.VISIBLE
                mText.text = "99+"
            }


        }
    }


}