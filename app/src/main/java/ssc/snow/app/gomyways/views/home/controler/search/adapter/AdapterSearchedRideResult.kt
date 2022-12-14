package ssc.snow.app.gomyways.views.home.controler.search.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orhanobut.dialogplus.ViewHolder
import kotlinx.android.synthetic.main.layout_item_search_trips.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelSearchRideResult
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.views.home.controler.search.ScreenSearchDetailTrip

class AdapterSearchedRideResult(private val context: Activity, private val mListData: List<ModelSearchRideResult.Data?>?) :
        RecyclerView.Adapter<AdapterSearchedRideResult.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_item_search_trips, viewGroup, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {

        mListData!!.get(i)!!.apply {
            holder.apply {
                mDate.text = leaving
                mSeatLeft.text = "Seats left: ${pending_seats}"
                mSource.text = origin
                mDestination.text = destination
                mNameDriver.text = "${first_name} ${last_name}"
                mRatingBar.rating = avgRating!!.toFloat()
                mTxtReviewCount.text = "Reviews: ${number_of_reviews}"
                // set the request with the conditions
                // setRequestNo(bookings, mRequestNo)
                updateWithUrl(driver_image_url)


            }
            holder.mViewDetails.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id.toString()
                //  Send the control to the next screen for details

                val mIntent = Intent(context, ScreenSearchDetailTrip::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.mDate.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id.toString()
                //  Send the control to the next screen for details

                val mIntent = Intent(context, ScreenSearchDetailTrip::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.mSeatLeft.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id.toString()
                //  Send the control to the next screen for details

                val mIntent = Intent(context, ScreenSearchDetailTrip::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.mSource.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id.toString()
                //  Send the control to the next screen for details

                val mIntent = Intent(context, ScreenSearchDetailTrip::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.mDestination.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id.toString()
                //  Send the control to the next screen for details

                val mIntent = Intent(context, ScreenSearchDetailTrip::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.mNameDriver.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id.toString()
                //  Send the control to the next screen for details

                val mIntent = Intent(context, ScreenSearchDetailTrip::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.mImgImageDriver.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id.toString()
                //  Send the control to the next screen for details

                val mIntent = Intent(context, ScreenSearchDetailTrip::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.mRatingBar.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id.toString()
                //  Send the control to the next screen for details

                val mIntent = Intent(context, ScreenSearchDetailTrip::class.java)
                mIntent.apply {
                    context.startActivity(mIntent)
                }
            }
            holder.mTxtReviewCount.setOnClickListener {

                // save the trip id for the for the details
                InjectorUtil.mTripId = trip_id.toString()
                //  Send the control to the next screen for details

                val mIntent = Intent(context, ScreenSearchDetailTrip::class.java)
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
        val mNameDriver = view.txt_name_user
        val mViewDetails = view.txt_view_details
        val mImgImageDriver = view.img_user
        val mRatingBar = view.ratingBar
        val mTxtReviewCount = view.txt_review_count

        //   val mRequestNo = view.txt_request_numbers
        // val mTxtViewDetails = view.txt_view_details
        // val mLlLayout = view.ll_root_clickable

        fun setRequestNo(bookings: Int?, mRequestNo: TextView) {
            mRequestNo.text = if (bookings == 0) "No matches, yet" else "No of bookings: ${bookings}"
        }

        fun updateWithUrl(url: String?) {
            // load image from network in case first laoding and secod time from cache
            Glide.with(itemView.context)
                    .load(url)
                    .into(mImgImageDriver)
        }

    }
}

