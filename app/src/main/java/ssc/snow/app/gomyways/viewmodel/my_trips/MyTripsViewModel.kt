package ssc.snow.app.gomyways.viewmodel.my_trips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ssc.snow.app.gomyways.data.model.ModelMyTrips
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class MyTripsViewModel : ViewModel() {


    var mResponseUpcomingTrips: MutableLiveData<ModelMyTrips?>
    var mResponseRecentTrips: MutableLiveData<ModelMyTrips?>
    var mRequestReceived: MutableLiveData<String>


    init {

        mResponseUpcomingTrips = MutableLiveData()
        mResponseRecentTrips = MutableLiveData()
        mRequestReceived = MutableLiveData()


        // Initialise mutable data to listen live data
        mResponseUpcomingTrips.postValue(null)
        mResponseRecentTrips.postValue(null)
        mRequestReceived.postValue(InjectorUtil.TRIP_REQUEST_RECEIVED + InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user?.token)

    }

    fun getUpcomingTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            val mResp = async {
                InjectorUtil.repository.getMyTrips(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, "upcoming")
            }

            // Send the value to the mutable data so that live data can be reflect on the views
            mResponseUpcomingTrips.postValue(mResp.await())

        }
    }

    fun getRecentTrips() {
        viewModelScope.launch(Dispatchers.IO) {

            val mResp = async {

                InjectorUtil.repository.getMyTrips(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, "recent")
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mResponseRecentTrips.postValue(mResp.await())

        }
    }

    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveUpcoming(): LiveData<ModelMyTrips?> = mResponseUpcomingTrips

    fun liveRecent(): LiveData<ModelMyTrips?> = mResponseRecentTrips
    fun liveRequestReceived(): LiveData<String> = mRequestReceived


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}