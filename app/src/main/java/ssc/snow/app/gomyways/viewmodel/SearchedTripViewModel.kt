package ssc.snow.app.gomyways.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import ssc.snow.app.gomyways.data.network.ApiRepository
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class SearchedTripViewModel : ViewModel() {

    val mRepository: ApiRepository = ApiRepository()


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(mToken: String) = liveData(Dispatchers.IO) {

        emit(mRepository.getPostTripStatus(mToken))
    }

    fun liveSearchedTripResponse() = liveData(Dispatchers.Main) {

        emit("${InjectorUtil.SEARCHED_TRIP_DETAILS.replace("LOGGED_TOKEN", InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user?.token.toString()).replace("TRIP_ID", InjectorUtil.mTripId)}")
    }


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}