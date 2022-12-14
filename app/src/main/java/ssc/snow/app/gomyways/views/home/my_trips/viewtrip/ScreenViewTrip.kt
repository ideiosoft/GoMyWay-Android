package ssc.snow.app.gomyways.views.home.my_trips.viewtrip

import android.graphics.Paint
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_screen_view_trip.*
import kotlinx.android.synthetic.main.layout_vehicle_detail_in_trip_details.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelPostTripDetail
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.data.utility.shareMessage
import ssc.snow.app.gomyways.viewmodel.my_trips.ViewTripDetailViewModel
import ssc.snow.app.gomyways.views.home.controler.ScreenBookToRequest
import ssc.snow.app.gomyways.views.home.controler.post_request.ScreenEditPostedTrip


class ScreenViewTrip : CommonActivity() {


    private lateinit var mViewModel: ViewTripDetailViewModel
    private var mTripOpenCloseStatus: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_view_trip)

        // onlciks
        init()
        onClicks()

    }

    override fun onStart() {
        super.onStart()

        // share Trip with friends link


        if (isNetworkConnected)
            mViewModel.getViewTripDetails(InjectorUtil.mTripId)
        else {
            showToast(resources.getString(R.string.provide_internet))
            return
        }

    }

    private fun init() {

        setSupportActionBar(rel_top)

        // initialisation of view model
        mViewModel = ViewModelProviders.of(this).get(ViewTripDetailViewModel::class.java)

        // Observing data from the server
        mViewModel.liveTripDetails().observe(this, Observer {

            when (it?.status) {

                true -> {
                    // Save the data for the route check on the map
                    InjectorUtil.tripDetail = it

                    // update the data to the UI with the response from the server
                    updateUI(it.data)
                }
            }

//            if (it != null) {
//
//            }
        })
        // Observing data from the server
        mViewModel.liveTripOps().observe(this, Observer {
            if (it != null) {
                if (it.status) showToast(it.message) else showToast(it.message)
            }
        })

    }

    val mStopPoints = hashSetOf<String>()
    fun updateUI(data: ModelPostTripDetail.Data?) {

        data!!.viewTrip!!.run {

            txt_pickup.text = origin
            txt_drop_off.text = destination

            txt_leaving_time.text = "Leaving at: ${leaving}"
            txt_seat_left.text = "Left seats: ${pending_seats}"
            txt_price_per_seat.text = "NGN ${price_per_seat} :Per seat"

            txt_description.text = description

            txt_returning_time.text = if (trip_type.equals("1")) {

                txt_returning_time.visibility = View.VISIBLE
                "Returning at: ${returning}"

            } else
                ""

            if (data.trip_stop_points!!.isNotEmpty()) {

                ll_sp.visibility = View.VISIBLE

                for (mLD: ModelPostTripDetail.Data.TripStopPoint? in data.trip_stop_points) {
                    mStopPoints.add(mLD!!.stop_point.toString())
                }

                txt_stop_points.text = mStopPoints.joinToString(" || ")
            }


            sw_trip_open_close.isChecked = trip_status.equals("0")
            txt_max_2_people.text = if (back_row_seating.equals("0")) "Max 2 people in the back" else "Max 3 people in the back"
            txt_medium_luggage.text = if (others.equals("0")) "No Luggage" else if (others.equals("1")) " Medium Luggage" else "Large Luggage"

            // Set the materials to carry
            setMaterials(others)
            // set vehicle details

            txt_name_car.text = vehicle_name
            txt_vehicle_type_value.text = vehicle_type
            txt_model_value.text = model
            txt_plate_num.text = plate_number

            // Set image of vehicle
            loadImageWithGlideBitmap(img_center_car, vehicle_url_path?.split(",")?.get(0))
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


    private fun onClicks() {

        img_back.setOnClickListener {
            this@ScreenViewTrip.finish()

        }

        sw_trip_open_close.setOnClickListener {

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }

            mTripOpenCloseStatus = if (sw_trip_open_close.isChecked) {
                "0"
            } else "1"

            mViewModel.tripCloseOpen(InjectorUtil.mTripId, mTripOpenCloseStatus)

        }


        txt_request_a_book.setOnClickListener {
            goForNextScreenWithoutFinish(ScreenBookToRequest::class.java)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.option_my_trips, menu)
        val item = menu!!.findItem(R.id.option_mark_as_complete)
        val option_route = menu!!.findItem(R.id.option_route)
        val option_edit_trip = menu!!.findItem(R.id.option_edit_trip)
        val option_cancel_trip = menu!!.findItem(R.id.option_cancel_trip)
        val option_mark_as_complete = menu!!.findItem(R.id.option_mark_as_complete)
        val option_edit_price = menu!!.findItem(R.id.option_edit_price)
      if(intent.hasExtra("type")) {
          item.isVisible = false //
          option_route.isVisible = false //
          option_edit_trip.isVisible = false //
          option_cancel_trip.isVisible = false //
          option_mark_as_complete.isVisible = false //
          option_edit_price.isVisible = false //
      }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.option_route -> {
                goForNextScreenWithoutFinish(TripMapViewWithDirection::class.java)
                true
            }

            R.id.option_edit_trip, R.id.option_edit_price -> {

                if (InjectorUtil.mBookingsCount!! <= 0)
                    goForNextScreenWithoutFinish(ScreenEditPostedTrip::class.java)
                else
                    warningBox("Sorry, you can not edit!")
                true
            }

//            R.id.option_edit_price -> {
//                if (InjectorUtil.mBookingsCount!! > 0)
//                goForNextScreenWithoutFinish(ScreenEditPostedTrip::class.java)
//
//
//
//                true
//            }

            R.id.option_cancel_trip -> {
                confirmCancelTripDialog("Are you sure you want to cancel this trip?")
                true
            }

            R.id.option_mark_as_complete -> {
                markAsCompleteTripDialog("Are you sure you want to mark as complete trip?")
                return true
            }

            R.id.option_share_trip -> {
                //  showToast(item.title.toString())

                var mSharedLink = InjectorUtil.SHARE_TRIP.replace("SHARE_TRIP_ID", InjectorUtil.mTripId)
                shareMessage(mSharedLink)

                return true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }


    fun confirmCancelTripDialog(msg: String?) {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Confirmation")
                .setContentText(Html.fromHtml(msg).toString())
                .setConfirmButton("Confirm") { sDialog ->
                    sDialog.dismissWithAnimation()
                    if (!isNetworkConnected) {
                        showToast(resources.getString(R.string.provide_internet))

                    } else {
                        mViewModel.tripCancel(InjectorUtil.mTripId, "3")
                    }
                }
                .setCancelButton("Cancel") { sDialog ->
                    sDialog.dismissWithAnimation()
                }
                .show()
    }

    fun markAsCompleteTripDialog(msg: String?) {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Confirmation")
                .setContentText(Html.fromHtml(msg).toString())
                .setConfirmButton("Confirm") { sDialog ->
                    sDialog.dismissWithAnimation()

                    if (!isNetworkConnected) {
                        showToast(resources.getString(R.string.provide_internet))

                    } else {
                        mViewModel.tripMarkComplete(InjectorUtil.mTripId, "2")
                    }

                }
                .setCancelButton("Cancel") { sDialog ->
                    sDialog.dismissWithAnimation()
                }
                .show()
    }
}
