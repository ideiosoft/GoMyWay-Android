package ssc.snow.app.gomyways.views.home.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_inbox_chat.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.inbox.Message
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class AdapterChat(
        private val context: Activity, private val mListMessages: ArrayList<Message?>) : RecyclerView.Adapter<AdapterChat.MyViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_item_inbox_chat, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        mListMessages.get(i)!!.apply {
            holder.apply {
                if (InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.id != from_id) {


                    mTextReceiverName.text = fromUser_firstName + " " + fromUser_lastName
                    mTextReceiver.text = message
                    mTextReceiverTime.text = time

                    mReceiverLayout.visibility = View.VISIBLE
                    mSenderLayout.visibility = View.GONE

                } else {

                    mTextSender.text = message
                    mTextSenderTime.text = time

                    mSenderLayout.visibility = View.VISIBLE
                    mReceiverLayout.visibility = View.GONE
                }

            }


        }
    }

    override fun getItemCount(): Int {
        return mListMessages.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTextReceiverName = view.txt_msg_receiver_name
        val mTextReceiver = view.msg_reciever
        val mTextReceiverTime = view.time
        val mReceiverLayout = view.linear_reciver
        val mSenderLayout = view.card_sender
        val mTextSender = view.msg_sender
        val mTextSenderTime = view.sender_time

    }

}