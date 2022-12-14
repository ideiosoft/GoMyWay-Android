package ssc.snow.app.gomyways.views.home.my_rides

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_screen_my_ride_detail.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelRideDetail
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.my_rides.RideDetailViewModel

class ScreenMyRideDetail : CommonActivity() {

    private lateinit var mViewModel: RideDetailViewModel
    private var mFlagReview: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_my_ride_detail)

        // onClicks
        init()
        onClicks()
    }

    private fun init() {

        setSupportActionBar(rel_top)

        // Initialisation of view model
        mViewModel = ViewModelProviders.of(this).get(RideDetailViewModel::class.java)

        // Observing data from the server
        mViewModel.liveRideDetails().observe(this, Observer {
            when (it?.status) {
                true -> {
                    InjectorUtil.mModelRideDetails = it

                    // Update the data to the UI with the response from the server
                    updateUI(it.data)
                }
            }
        })

        // Observing data from the server
        mViewModel.liveRideOps().observe(this, Observer {

            when (it?.status) {
                true -> {
                    if (mFlagReview) {
                        mFlagReview = false
                        showToast(it.message)
                        refreshScreen(this@ScreenMyRideDetail)
                    } else {
                        this@ScreenMyRideDetail.finish(); showToast(it.message)
                    }
                }
                else -> {

                   // showToast(it?.message)
                    sw_cancel_ride.isChecked = false
                }
            }


        })

    }

    private fun onClicks() {

        img_back.setOnClickListener {
            this@ScreenMyRideDetail.finish()

        }

        sw_cancel_ride.setOnClickListener {

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }

            if (sw_cancel_ride.isChecked) mViewModel.rideCancel()
        }

        txt_review.setOnClickListener {
            ll_review.visibility = if (ll_review.isShown) View.GONE else View.VISIBLE
            txt_heading_review.visibility = if (txt_heading_review.isShown) View.GONE else View.VISIBLE

        }

        btn_submit_review.setOnClickListener {
            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }

            var mRating = rating_.rating.toInt().toString()
            var mComment = edt_review.text.toString()

            if (mRating.isNotEmpty() && !mRating.equals("0")) {
                mFlagReview = true
                mViewModel.submitReview(mRating, mComment)
            } else {
                showToast(resources.getString(R.string.need_to_rate))
            }

        }

    }

    override fun onStart() {
        super.onStart()

        if (isNetworkConnected)
            mViewModel.getRideDetail()
        else {
            showToast(resources.getString(R.string.provide_internet))
            return
        }
    }


    private fun updateUI(data: ModelRideDetail.Data?) {
        data!!.rideDetail!!.run {

            setTripStatus(status.toString(), txt_ride_status)
            if (status!!.equals("0") || status.equals("1")) {
                sw_cancel_ride.visibility = View.VISIBLE
                sw_cancel_ride.isChecked = false
            } else {
                sw_cancel_ride.visibility = View.GONE
            }


            txt_pickup.text = origin
            txt_drop_off.text = destination
            txt_leaving_time.text = "Leaving at: ${leaving}"
            txt_seat_left.text = "Bookings: ${seats_booked}"
            txt_price_per_seat.text = "NGN ${price_per_seat} :Per seat"
            txt_description.text = description
            txt_returning_time.text = if (trip_type.equals("1")) {
                txt_returning_time.visibility = View.VISIBLE
                "Returning at: ${returning}"
            } else
                ""

            ll_sp.visibility = if (stop_points.equals("".trim())) View.GONE else View.VISIBLE
            txt_stop_points.text = stop_points

            txt_request_approved_in_time.text = "Approved with in: ${approve_within} Hours"

            txt_max_2_people.text = if (back_row_seating.equals("0")) "Max 2 people in the back" else "Max 3 people in the back"
            txt_medium_luggage.text = if (others.equals("0")) "No Luggage" else if (others.equals("1")) " Medium Luggage" else "Large Luggage"

            // Set the materials to carry
            setMaterials(others)
            // set vehicle details

            txt_driver_name.text = driverFirst + " " + driverLast
            txt_driver_email.text = driverEmail

            // Set image of vehicle
            loadImageWithGlideBitmap(img_driver, driver_image_url)
        }
    }


    private fun setMaterials(others: String?) {

        strikeThroughTextView(txt_bike)
        strikeThroughTextView(txt_pets)
        strikeThroughTextView(txt_snowboard)
        strikeThroughTextView(txt_tyres)

        when (others) {

            "0" -> {
                txt_tyres.paintFlags = Paint.ANTI_ALIAS_FLAG
            }
            "1" -> {
                txt_bike.paintFlags = Paint.ANTI_ALIAS_FLAG
            }
            "2" -> {
                txt_snowboard.paintFlags = Paint.ANTI_ALIAS_FLAG
            }

            else -> {
                txt_pets.paintFlags = Paint.ANTI_ALIAS_FLAG
            }

        }

    }

    fun setTripStatus(mSt: String, mTxtStatus: TextView) {
        when (mSt) {
            "0" -> {
                mTxtStatus.text = "Ride: Pending"
            }
            "1" -> {
                mTxtStatus.text = "Ride: Accepted"
            }
            "2" -> {
                mTxtStatus.text = "Ride: Rejected"
            }
            "3" -> {
                mTxtStatus.text = "Ride: Completed"
                txt_review.visibility = View.VISIBLE
            }
            "4" -> {
                mTxtStatus.text = "Ride: Expired"
            }
            else -> {
                mTxtStatus.text = "Ride: Cancel By Passenger"
            }

            //   0=pending,1=accepted,2=rejected,3=completed,4=Expired,5=Cancel By Passenger
        }

    }

}
