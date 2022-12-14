package ssc.snow.app.gomyways.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ssc.snow.app.gomyways.data.model.ModelCommon
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class ForgotPasswordViewModel : ViewModel() {

    var mResponse: MutableLiveData<ModelCommon?>

    init {

        mResponse = MutableLiveData()
        // initialise mutable data to listen live data
        mResponse.postValue(null)

    }


    fun forgotPassword(mEmail: String) {
        viewModelScope.launch {

            val mResp = async {

                InjectorUtil.repository.forgotPass(mEmail)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mResponse.postValue(mResp.await())

        }
    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(): LiveData<ModelCommon?> = mResponse


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}