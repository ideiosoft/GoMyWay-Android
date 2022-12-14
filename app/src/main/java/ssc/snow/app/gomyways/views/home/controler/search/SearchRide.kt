package ssc.snow.app.gomyways.views.home.controler.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_ride.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelRecentSearches
import ssc.snow.app.gomyways.data.model.ModelSearchRideResult
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.data.utility.InjectorUtil.AUTOCOMPLETE_REQUEST_CODE
import ssc.snow.app.gomyways.views.home.controler.search.adapter.AdapterRecentSearches
import ssc.snow.app.gomyways.views.home.controler.search.viewmodel.SearchRideViewModel
import java.util.*
import java.util.Arrays.asList


class SearchRide : CommonActivity() {

    // AdapterRecentSearches(this)
    var mAdapterRecentSearches: AdapterRecentSearches? = null
    lateinit var mViewModel: SearchRideViewModel
    var mFlagClick: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_ride)


        // initialisations
        initCall()
        fetchingRecentSearches()

    }

    private fun fetchingRecentSearches() {
        mViewModel.liveResponse().observe(this, Observer {

            if (it != null) {
                if (it.status!!) {

                    // Set the count for the recent searches
                    txt_recent_searches.text = "Recent Searches(${it.data!!.size})"

                    // set recycler view
                    setRecyclerView(it)
                }
            }

        })

        if (isNetworkConnected) {
            mViewModel.getRecentSearches()
        }

    }

    private fun setRecyclerView(mModelData: ModelRecentSearches) {

        //  set adapter
        applicationContext?.let {
            mAdapterRecentSearches = AdapterRecentSearches(mModelData.data)
            recycle_recent_searches.adapter = mAdapterRecentSearches
        }


    }

    private fun initCall() {

        // initialisations
        mViewModel = ViewModelProviders.of(this).get(SearchRideViewModel::class.java)

        recycle_recent_searches.isFocusable = false
        recycle_recent_searches.requestFocus()
        // set the layout manager for the views
        recycle_recent_searches.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

        // img click to go back
        img_back.setOnClickListener {
            this.finish()
        }

        edt_from.setOnClickListener {
            mFlagClick = true
            onSearchCalled()
        }

        edt_to.setOnClickListener {
            mFlagClick = false
            onSearchCalled()
        }


        // Press search time
        btn_search.setOnClickListener {
            if (edt_from.text.trim().isNotEmpty() && edt_to.text.trim().isNotEmpty()) {

                if (!isNetworkConnected) {
                    showToast(resources.getString(R.string.provide_internet))
                    return@setOnClickListener
                }

                InjectorUtil.mToBeSearchRide = "${edt_from.text}|${edt_to.text}|${edt_leaving_option.text}"

                goFindRide(edt_from.text.toString(), edt_to.text.toString())
            } else
                showToast(resources.getString(R.string.field_cnt_empty))


            //  goForNextScreenWithoutFinish(ScreenSearchResult::class.java)
        }

        // get date dialog
        edt_leaving_option.setOnClickListener {
            uiHandlerMethod.getCalendarDialogDate(edt_leaving_option)
        }


    }


    fun goFindRide(mFrom: String, mTo: String) {
        //   String mDeviceToken = getSessionInstanceNotNull().getDeviceFCMToken();
        //   Log.wtf("mDeviceToken== ", mDeviceToken);

        if (!isNetworkConnected) {
            showToast(resources.getString(R.string.provide_internet))
            return
        }

        showIOSProgress()
        sessionInstance.loggedInUserDetail.data?.user?.token?.let {
            restfullInstance.searchRide(it, mFrom, mTo,
                    edt_leaving_option.text.toString(),
                    mApiHandler)
        }
    }


    @SuppressLint("handlerLeak")
    private val mApiHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    dismissIOSProgress()
                    val mModeldata = msg.obj as ModelSearchRideResult

                    Log.wtf("++", "+++ mModel list size +++" + mModeldata.status)
                    if (mModeldata.status!!) {
                        InjectorUtil.mModelSearchResultRide = mModeldata
                        goForNextScreenWithoutFinish(ScreenSearchRideResult::class.java)
                    } else {
                        warningBox(mModeldata.message)
                    }
                }

                2 -> {

                    dismissIOSProgress()
                    showToast("failure: " + msg.obj)
                    Log.wtf("error: ", msg.obj.toString())

                }
            }
        }
    }


    fun onSearchCalled() { // Set the fields to specify which types of place data to return.
        val fields = asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS)
        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("NG") //NIGERIA
                .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                val place = Autocomplete.getPlaceFromIntent(data!!)
                //  Log.i(FragmentActivity.TAG, "Place: " + place.name + ", " + place.id + ", " + place.address)

                Log.wtf("SearchRide", "\nID: " + place.id + "\naddress:" + place.address + "\nName:" + place.name + "\nlatlong: " + place.latLng)
                // do query with address


                val geocoder: Geocoder
                val addresses: List<Address>
                geocoder = Geocoder(this, Locale.getDefault())

             //   addresses = geocoder.getFromLocation(place.latLng!!.latitude, place.latLng!!.longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

/*

                val address: String = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                val city: String = addresses[0].getLocality()
                val state: String = addresses[0].getAdminArea()
                val country: String = addresses[0].getCountryName()

                val knownName: String = addresses[0].getFeatureName()

*/

                Log.e("placee", Gson().toJson(place))
              //  Log.e("addresses",  addresses.toString())
                if (mFlagClick) {
                    if(place.name.equals("MKO Abiola Way"))
                    {
                        edt_from.setText("Ring Road")

                    }
                    else{
                    edt_from.setText(place.name)}
                } else {
                    if(place.name.equals("MKO Abiola Way"))
                    {
                        edt_to.setText("Ring Road")

                    }
                    else{
                        edt_to.setText(place.name)}
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) { // TODO: Handle the error.
                val status: Status = Autocomplete.getStatusFromIntent(data!!)

                Toast.makeText(this@SearchRide, "Error: " + status.statusMessage, Toast.LENGTH_LONG).show()
                // Log.i(FragmentActivity.TAG, status.statusMessage)


            } else if (resultCode == Activity.RESULT_CANCELED) { // The user canceled the operation.


            }
        }
    }
}

