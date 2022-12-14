package ssc.snow.app.gomyways.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ssc.snow.app.gomyways.data.model.ModelAllNotifications
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class NotificationsViewModel : ViewModel() {

    var mNotiResponse: MutableLiveData<ModelAllNotifications>

    init {
        mNotiResponse = MutableLiveData()

        // initialise mutable data to listen live data
        mNotiResponse.postValue(null)
    }

    fun getNotifications() {
        viewModelScope.launch {
            val mResp = async {
               InjectorUtil.repository.getAllNotifications(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token)

            }

            // Send the value to the mutable data so that live data can be reflect on the views
            mNotiResponse.postValue(mResp.await())

        }
    }


    /* ***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(): LiveData<ModelAllNotifications?> = mNotiResponse

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}