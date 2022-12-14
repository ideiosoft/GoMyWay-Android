package ssc.snow.app.gomyways.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ssc.snow.app.gomyways.data.model.ModelNotificationSettings
import ssc.snow.app.gomyways.data.network.ApiRepository

class NotificationSettingsViewModel : ViewModel() {

    val mRepository: ApiRepository = ApiRepository()

    var mResponse: MutableLiveData<ModelNotificationSettings?>


    init {

        mResponse = MutableLiveData()

        // initialise mutable data to listen live data
        mResponse.postValue(null)

    }


    fun getNotifications(mToken: String) {
        viewModelScope.launch {

            val mResp = async {

                mRepository.getNotificaionSettings(mToken)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mResponse.postValue(mResp.await())

        }
    }

    fun saveNotificaionSettins(mToken: String, mSms: String, mEmail: String, mPush: String) {
        viewModelScope.launch {
            val mResp = async {

                mRepository.saveNotificationSettings(mToken, mSms, mEmail, mPush)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mResponse.postValue(mResp.await())

        }
    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveNotifications(): LiveData<ModelNotificationSettings?> = mResponse


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}