package ssc.snow.app.gomyways.views.home.my_rides.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_item_recent_rides.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelMyRides
import ssc.snow.app.gomyways.data.utility.InjectorUtil

import ssc.snow.app.gomyways.views.home.my_rides.ScreenMyRideDetail

class AdapterRecentRides(private val context: Activity, private val mListData: List<ModelMyRides.Data?>?) :
        RecyclerView.Adapter<AdapterRecentRides.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_item_recent_rides, viewGroup, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        mListData!!.get(i)!!.apply {
            holder.apply {
                mDate.text = leaving
                mPrice.text = "NGN ${total_amount}"
                mBookedSeats.text = "Bookings: ${seats_booked}"
                mSource.text = origin
                mDestination.text = destination

                //  set the request with the conditions
                //  setRequestNo(bookings, mRequestNo)
                setTripStatus(status.toString(), mTripStatus)
                mDriverName.text = "${driverFirst} ${driverLast}"

                // set the request with the conditions
                updateWithUrl(driver_image_url)

            }
            holder.txt_view_detailss.setOnClickListener {
                //  Save the trip id for the for the details
                //  InjectorUtil.mTripId = trip_id
                //  InjectorUtil.mBookingsCount = bookings

                InjectorUtil.mRequestId = mListData!!.get(i)!!.request_id.toString()

                // Send the control to the next screen for details
                val mIntent = Intent(context, ScreenMyRideDetail::class.java)
                mIntent.putExtra("type","recent")
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }

            holder.mLlRootClick.setOnClickListener {

                InjectorUtil.mRequestId = request_id.toString()

                // send the control to the next screen for details
                val mIntent = Intent(context, ScreenMyRideDetail::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }

            }

        }


    }


    override fun getItemCount(): Int {
        return mListData!!.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mDate = view.txt_outer_date
        val mPrice = view.txt_seat_left
        val mBookedSeats = view.txt_seats_booked
        val mSource = view.txt_source
        val mDestination = view.txt_destination
        val mDriverName = view.txt_name_user
        val mTripStatus = view.txt_view_details
        val txt_view_detailss = view.txt_view_detailss
        val mImgDriver = view.img_user
        val mLlRootClick = view.ll_root_clickable

        fun updateWithUrl(url: String?) {

            // load image from network in case first laoding and secod time from cache
            Glide.with(itemView.context)
                    .load(url)
                    .into(mImgDriver)
        }

        fun setTripStatus(mSt: String, mTxtStatus: TextView) {
            when (mSt) {
                "0" -> {
                    mTxtStatus.text = "Pending"
                }
                "1" -> {
                    mTxtStatus.text = "Accepted"
                }
                "2" -> {
                    mTxtStatus.text = "Rejected"
                }
                "3" -> {
                    mTxtStatus.text = "Completed"
                }
                "4" -> {
                    mTxtStatus.text = "Expired"
                }
                else -> {
                    mTxtStatus.text = "Cancel By Passenger"
                }

                //   0=pending,1=accepted,2=rejected,3=completed,4=Expired,5=Cancel By Passenger
            }

        }

    }
}

