package ssc.snow.app.gomyways.views.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_upcoming_requests.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelLogin


class AdapterUpcomingRequestsInProfile(
        private val context: Context,
        private var mListData: MutableList<ModelLogin.Data.UpcomingTrip>
) : RecyclerView.Adapter<AdapterUpcomingRequestsInProfile.ViewHolder>() {

    private var mData: MutableList<ModelLogin.Data.UpcomingTrip>

    init {
        mData = mListData
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.row_upcoming_requests, parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTxtSeat.text = mData.get(position).available_seats + "seats\n available"
        holder.mTxtTrip.text = mData.get(position).origin + " to " + mData.get(position).destination
        holder.mTxtDate.text = mData.get(position).leaving

        // load image from network incase first laoding and secod time from cache
        holder.updateWithUrl("http://112.196.5.115:80/go_my_ways/uploads/users/" + mData.get(position).profile_url)

        holder.itemView.setOnClickListener {
            // Toast.makeText(context, "Hahahaha", Toast.LENGTH_LONG).show()
            // mCallback.getDetails("1")
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mImg = view.img_user_trip
        val mTxtTrip = view.txt_trip_from_to
        val mTxtDate = view.txt_date_trip
        val mTxtSeat = view.txt_date_trip


        fun updateWithUrl(url: String?) {
            // load image from network in case first laoding and secod time from cache
            Glide.with(itemView.context)
                    .load(url)
                    .into(mImg)
        }
    }
}
