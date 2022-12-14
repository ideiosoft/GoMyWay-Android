package ssc.snow.app.gomyways.views.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_row_rewards.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelRewards


class AdapterRewards(
        private val context: Context,
        private val mListData: List<ModelRewards.Data.User?>?) : RecyclerView.Adapter<AdapterRewards.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.layout_row_rewards, parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return mListData!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mListData!!.get(position)!!.run {

            // holder.mQuestion.text = "● ${title}"
            holder.mName.text = "${referredUser}"
            holder.mDate.text = "${created_at}"
            holder.mPrice.text = "NGN ${commission_amount}"

            // load image

            if (image_url != null) {
                holder.updateWithUrl(image_url)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mImgUser = view.img_user_rewards
        val mName = view.txt_name_reward
        val mDate = view.txt_date_reward
        val mPrice = view.txt_reward_price

        fun updateWithUrl(url: String?) {
            // load image from network in case first laoding and secod time from cache
            Glide.with(itemView.context)
                    .load(url)
                    .into(mImgUser)
        }

    }


}
