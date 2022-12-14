package ssc.snow.app.gomyways.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ssc.snow.app.gomyways.data.model.ModelLogin
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class SocialLoginViewModel : ViewModel() {


    var mResponse = MutableLiveData<ModelLogin>().apply {
        value = null
    }


    fun socialLogin(mType: String, mSocialId: String, mEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val mResp = async {
                InjectorUtil.repository.socialLogin(InjectorUtil.sessionNotNullGoMyWay().deviceFCMToken, mType, mSocialId, mEmail)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mResponse.postValue(mResp.await())

        }
    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(): LiveData<ModelLogin?> = mResponse


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}