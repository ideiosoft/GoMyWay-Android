package ssc.snow.app.gomyways.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import ssc.snow.app.gomyways.data.network.ApiRepository
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class PostTripViewModel : ViewModel() {

    val mRepository: ApiRepository = ApiRepository()


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(mToken: String) = liveData(Dispatchers.IO) {

        emit(mRepository.getPostTripStatus(mToken))
    }

    fun liveEditTripResponse() = liveData(Dispatchers.Main) {

        emit("${InjectorUtil.EDIT_POST_TRIP_URL.replace("LOGGED_TOKEN", InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user?.token.toString()).replace("USER_ID", InjectorUtil.mTripId)}")
    }


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}