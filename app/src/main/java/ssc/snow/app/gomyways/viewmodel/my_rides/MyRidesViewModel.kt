package ssc.snow.app.gomyways.viewmodel.my_rides

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ssc.snow.app.gomyways.data.model.ModelMyRides
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class MyRidesViewModel : ViewModel() {


    var mResponseUpcomingRides: MutableLiveData<ModelMyRides?>
    var mResponseRecentRides: MutableLiveData<ModelMyRides?>



    init {
        mResponseUpcomingRides = MutableLiveData()
        mResponseRecentRides = MutableLiveData()


        // Initialise mutable data to listen live data
        mResponseUpcomingRides.postValue(null)
        mResponseRecentRides.postValue(null)


    }

    fun getUpcomingTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            val mResp = async {
                InjectorUtil.repository.getMyRides(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, "upcoming")
            }

            // Send the value to the mutable data so that live data can be reflect on the views
            mResponseUpcomingRides.postValue(mResp.await())

        }
    }

    fun getRecentTrips() {
        viewModelScope.launch(Dispatchers.IO) {

            val mResp = async {

                InjectorUtil.repository.getMyRides(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, "recent")
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mResponseRecentRides.postValue(mResp.await())

        }
    }

    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveUpcoming(): LiveData<ModelMyRides?> = mResponseUpcomingRides

    fun liveRecent(): LiveData<ModelMyRides?> = mResponseRecentRides



    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}