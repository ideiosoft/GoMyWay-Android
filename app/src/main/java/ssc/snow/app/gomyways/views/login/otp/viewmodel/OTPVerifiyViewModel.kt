package ssc.snow.app.gomyways.views.login.otp.viewmodel

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

class OTPVerifiyViewModel : ViewModel() {

    var mResponse: MutableLiveData<ModelCommon?>
    var mResponseResend: MutableLiveData<ModelCommon?>

    var mPhoneNumber: MutableLiveData<String>

    init {

        mResponse = MutableLiveData()
        mPhoneNumber = MutableLiveData()
        mResponseResend = MutableLiveData()

        // initialise mutable data to listen live data
        mResponse.postValue(null)
        mResponseResend.postValue(null)
        mPhoneNumber.postValue(InjectorUtil.mPhoneNumber)


    }


    fun confirmCode(mCode: String, mToken: String) {
        viewModelScope.launch {

            val mResp = async {
                InjectorUtil.repository.confirmCode(mCode, mToken)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mResponse.postValue(mResp.await())

        }
    }



    fun phoneVerification(mPhone: String, mToken: String) {
        viewModelScope.launch {

            val mResp = async {

                InjectorUtil.repository.phoneVerification(mPhone, mToken)
            }

            // Send the value to the mutable data so that live data can be reflect on the views
            mResponseResend.postValue(mResp.await())

        }
    }

    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(): LiveData<ModelCommon?> = mResponse
    fun liveResponseResend(): LiveData<ModelCommon?> = mResponseResend

    fun liveResponsePhoneNumber(): LiveData<String> = mPhoneNumber


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}