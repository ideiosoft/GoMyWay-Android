package ssc.snow.app.gomyways.views.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_item_notifications.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelAllNotifications

class AdapterNotifications(private val dataList: List<ModelAllNotifications.Data?>?) : RecyclerView.Adapter<AdapterNotifications.MyViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_item_notifications, viewGroup, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, i: Int) { //
        dataList!!.get(i)!!.apply {
            holder.apply {
                mTxtName.text = "${first_name} ${last_name}"
                mTxtDesc.text = notification
                mTxtTime.text = time

                // load image
                updateWithUrl(profile_image_url)
            }
        }


        holder.itemView.setOnClickListener {
            // send the control to the next screen for details
            //   context.startActivity(new Intent(context, ScreenInboxDetail.class));
        }
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTxtName = view.txt_name
        val mTxtDesc = view.txt_grp_description
        val mTxtTime = view.txt_time
        val mImgUser = view.img_user

        fun updateWithUrl(url: String?) {
            // load image from network in case first laoding and secod time from cache
            Glide.with(itemView.context)
                    .load(url)
                    .into(mImgUser)
        }
    }


}