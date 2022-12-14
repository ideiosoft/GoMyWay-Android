package ssc.snow.app.gomyways.views.home.my_rides.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_item_rides.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelMyRides
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.views.home.my_rides.ScreenMyRideDetail

class AdapterUpcomingRides(private val context: Context, private val mListData: List<ModelMyRides.Data?>?) :
        RecyclerView.Adapter<AdapterUpcomingRides.MyViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_item_rides, viewGroup, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {

        mListData!!.get(i)!!.apply {
            holder.apply {
                mDate.text = leaving
                mBookingPrice.text = "NGN ${total_amount}"
                mBookedSeats.text = "Bookings: ${seats_booked}"
                mSource.text = origin
                mDestination.text = destination
                mDriverName.text = "${driverFirst} ${driverLast}"

                // Set the request with the conditions
                updateWithUrl(driver_image_url)

            }

            holder.mTxtViewDetails.setOnClickListener {
            //  Save the trip id for the for the details
            //  InjectorUtil.mTripId = trip_id
            //  InjectorUtil.mBookingsCount = bookings

                InjectorUtil.mRequestId = request_id.toString()

                // Send the control to the next screen for details
                val mIntent = Intent(context, ScreenMyRideDetail::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.llroot_click.setOnClickListener {

                InjectorUtil.mRequestId = request_id.toString()

                // Send the control to the next screen for details
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
        val mBookingPrice = view.txt_seat_left
        val mBookedSeats = view.txt_seats_booked
        val mSource = view.txt_source
        val mDestination = view.txt_destination
        val mDriverName = view.txt_name_user
        val mTxtViewDetails = view.txt_view_details
        val mImgDriver = view.img_user
        val llroot_click= view.ll_root_clickable

        fun updateWithUrl(url: String?) {
            // load image from network in case first laoding and secod time from cache
            Glide.with(itemView.context)
                    .load(url)
                    .into(mImgDriver)
        }

    }
}

