package ssc.snow.app.gomyways.viewmodel.my_rides

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ssc.snow.app.gomyways.data.model.ModelCommon
import ssc.snow.app.gomyways.data.model.ModelRideDetail
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class RideDetailViewModel : ViewModel() {

    var mRideDetails: MutableLiveData<ModelRideDetail?>
    var mRideOps: MutableLiveData<ModelCommon?>

    init {

        mRideDetails = MutableLiveData()
        mRideOps = MutableLiveData()

        // initialise mutable data to listen live data
        mRideDetails.postValue(null)
        mRideOps.postValue(null)

    }


    fun getRideDetail() {
        viewModelScope.launch {
            val mResp = async(Dispatchers.IO) {
                InjectorUtil.repository.viewRideDetail(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, InjectorUtil.mRequestId)
            }
            // Send the value to the mutable data so that live data can be reflect on the views
            mRideDetails.postValue(mResp.await())
        }
    }


    fun rideCancel() {
        viewModelScope.launch {
            val mResp = async(Dispatchers.IO) {
                InjectorUtil.repository.cancelRide(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, InjectorUtil.mRequestId, "5")
            }
            // Send the value to the mutable data so that live data can be reflect on the views
            mRideOps.postValue(mResp.await())

        }
    }


    fun submitReview(mRating: String, mComment: String) {
        viewModelScope.launch {
            val mResp = async(Dispatchers.IO) {
                InjectorUtil.repository.submitReview(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, InjectorUtil.mRequestId,
                        mRating,
                        InjectorUtil.mModelRideDetails!!.data!!.rideDetail!!.posted_trip_id.toString(),
                        InjectorUtil.mModelRideDetails!!.data!!.rideDetail!!.posted_trip_stops_id.toString(),
                        InjectorUtil.mRequestId,
                        InjectorUtil.mModelRideDetails!!.data!!.rideDetail!!.passenger_id.toString(),
                        InjectorUtil.mModelRideDetails!!.data!!.rideDetail!!.driver_id.toString(),
                        mComment)
            }
            // Send the value to the mutable data so that live data can be reflect on the views
            mRideOps.postValue(mResp.await())

        }
    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveRideDetails(): LiveData<ModelRideDetail?> = mRideDetails

    fun liveRideOps(): LiveData<ModelCommon?> = mRideOps


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}