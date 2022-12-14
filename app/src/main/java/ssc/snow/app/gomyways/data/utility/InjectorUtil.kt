package ssc.snow.app.gomyways.data.utility

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import android.widget.Toast
import ssc.snow.app.gomyways.data.CheckConnectivity
import ssc.snow.app.gomyways.data.model.ModelPostTripDetail
import ssc.snow.app.gomyways.data.model.ModelRideDetail
import ssc.snow.app.gomyways.data.model.ModelSearchRideResult
import ssc.snow.app.gomyways.data.network.ApiRepository
import ssc.snow.app.gomyways.data.network.google_network.ApiRepositoryGoogle
import ssc.snow.app.gomyways.data.session.SessionGomyway
import ssc.snow.app.gomyways.data.session.SessionNotNull
import ssc.snow.app.gomyways.views.MyApplication

object InjectorUtil {

    val AUTOCOMPLETE_REQUEST_CODE = 1
    val GOOGLE_API_KEY = "AIzaSyBvnYPa4tw9s5TSGwzePeWD4Kk7yulyy9c"


    // Save trip details
    var mTripId: String = ""
    // Save Model for the stop points
    var tripDetail: ModelPostTripDetail? = null

    var mBookingsCount: Int? = 0

    // http://112.196.5.115/go_my_ways
    // Constant web view Urls related to the Post Trips http://gomywayride.com/
    val POST_TRIP_URL = "https://gomywayride.com/api/Apidata/post-trip?token="
    val TRIP_SUBMIT_SUCCESSFULLY = "https://gomywayride.com/?token="

    // constant url for the edit trip
    var EDIT_POST_TRIP_URL = "https://gomywayride.com/api/Apidata/edit-post-trip?token=LOGGED_TOKEN&id=USER_ID"
    // request received on trips
    val TRIP_REQUEST_RECEIVED = "https://gomywayride.com/api/Apidata/get-trip-requests?token="

    // share trip link
    var SHARE_TRIP = "https://gomywayride.com/passenger/search-trip-detail/SHARE_TRIP_ID"

    const val WALLET_EARNING = " https://gomywayride.com/api/Apidata/get-wallet-listing?token="


    /****
     * Passenger  Module
     * ****/
    val SEARCHED_TRIP_DETAILS = "https://gomywayride.com/api/Apidata/search-trip-detail?token=LOGGED_TOKEN&id=TRIP_ID"
    val BOOKED_TRIP_CONGRATESS = "https://gomywayride.com/?token="
    val BOOKED_TRIP_CONGRATES = "https://gomywayride.com/?token=LOGGED_TOKEN"


    // get the repository singleton instance
    val repository = ApiRepository()
    val repositoryGoogle = ApiRepositoryGoogle()

    // check whether the
    fun isNetworkConnected(): Boolean {
        return CheckConnectivity(MyApplication.applicationContext()).isNetworkAvailable!!
    }

    fun networkCheck() {
        if (!isNetworkConnected()) {
            showToast("Provide internet!")
            return
        }
    }

    fun showToast(toast_string: String) {
        Toast.makeText(MyApplication.applicationContext(), toast_string, Toast.LENGTH_SHORT).show()
    }

    fun sessionGoMyWay(): SessionGomyway {
        return SessionGomyway(MyApplication.applicationContext())
    }

    fun sessionNotNullGoMyWay(): SessionNotNull {
        return SessionNotNull(MyApplication.applicationContext())
    }

    fun strikeThroughTextView(mTextView: TextView) {
        mTextView.paintFlags = mTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }


    /****
     * Ride module rides
     * ****/
    var mToBeSearchRide = ""
    var mModelSearchResultRide: ModelSearchRideResult? = null
    var mModelRideDetails: ModelRideDetail? = null


    // Save trip details
    var mRequestId: String = ""

    /****
     * Chat module
     * ****/
    var mConversationId = ""
    var mToId = ""

    //......//.........//........//..........

//    sealed class Expr
//    data class Const(val number: Double) : Expr()
//    data class Sum(val e1: Expr, val e2: Expr) : Expr()
//    object NotANumber : Expr()
//
//    fun eval(expr: Expr): Double = when(expr) {
//        is Const -> expr.number
//        is Sum -> eval(expr.e1) + eval(expr.e2)
//        NotANumber -> Double.NaN
//        // the `else` clause is not required because we've covered all the cases
//    }

    // for phone verifications
    var mPhoneNumber: String = ""


    fun View.showMsgInInjector(mMd: String) {
        Toast.makeText(this.context, mMd, Toast.LENGTH_SHORT).show()
    }

}