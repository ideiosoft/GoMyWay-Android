package ssc.snow.app.gomyways.views.home.controler.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ssc.snow.app.gomyways.data.model.ModelRecentSearches
import ssc.snow.app.gomyways.data.model.ModelSearchRideResult
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class SearchRideViewModel : ViewModel() {

    var mResponse: MutableLiveData<ModelRecentSearches?>
    var mSearchresult: MutableLiveData<ModelSearchRideResult?>


    init {

        mResponse = MutableLiveData()
        mSearchresult = MutableLiveData()

        // initialise mutable data to listen live data
        mResponse.postValue(null)
        mSearchresult.postValue(InjectorUtil.mModelSearchResultRide)
    }

    fun getRecentSearches() {
        viewModelScope.launch {

            val mResp = async {

                InjectorUtil.repository.getRecentSearches(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token)
            }

            // Send the value to the mutable data so that live data can be reflect on the views
            mResponse.postValue(mResp.await())

        }
    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(): LiveData<ModelRecentSearches?> = mResponse

    fun liveResponseSearchRideResult(): LiveData<ModelSearchRideResult?> = mSearchresult


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}