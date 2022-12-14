package ssc.snow.app.gomyways.views.home.my_trips.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_trips.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelMyTrips
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.views.home.my_trips.viewtrip.ScreenViewTrip

class AdapterUpcomingTrips(private val context: Context, private val mListData: List<ModelMyTrips.Data?>?) :
        RecyclerView.Adapter<AdapterUpcomingTrips.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_item_trips, viewGroup, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {

        mListData!!.get(i)!!.apply {
            holder.apply {
                mDate.text = leaving
                mSeatLeft.text = "${pending_seats} :Seat left"
                mSource.text = origin
                mDestination.text = destination

                // set the request with the conditions
                setRequestNo(bookings, mRequestNo)

            }
            holder.mTxtViewDetailss.visibility=View.GONE
            holder.mTxtViewDetails.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id
                InjectorUtil.mBookingsCount = bookings

                // Send the control to the next screen for details
                val mIntent = Intent(context, ScreenViewTrip::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.mLlLayout.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id
                InjectorUtil.mBookingsCount = bookings

                // Send the control to the next screen for details
                val mIntent = Intent(context, ScreenViewTrip::class.java)
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
        val mSeatLeft = view.txt_seat_left
        val mSource = view.txt_source
        val mDestination = view.txt_destination
        val mRequestNo = view.txt_request_numbers
        val mTxtViewDetails = view.txt_view_details
        val mTxtViewDetailss = view.txt_view_detailss
        val mLlLayout = view.ll_root_clickable

        fun setRequestNo(bookings: Int?, mRequestNo: TextView) {
            mRequestNo.text = if (bookings == 0) "No matches, yet" else "No of bookings: ${bookings}"
        }


    }
}

