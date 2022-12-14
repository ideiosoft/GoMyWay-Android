package ssc.snow.app.gomyways.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ssc.snow.app.gomyways.data.model.ModelCommon
import ssc.snow.app.gomyways.data.model.ModelLogin
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class ImmediateAfterSignupViewModel : ViewModel() {

    var mResponse: MutableLiveData<ModelCommon> = MutableLiveData()
    var mResponseLogin: MutableLiveData<ModelLogin> = MutableLiveData()

    init {
        mResponse.postValue(null)
        mResponseLogin.postValue(null)

    }

    fun resendEmailLink() {
        viewModelScope.launch {

            val mResp = async {
                InjectorUtil.repository.resendEmail(InjectorUtil.sessionNotNullGoMyWay().afterSignupToken)
            }

            // Send the value to the mutable data so that live data can be reflect on the views
            mResponse.postValue(mResp.await())

        }
    }

//    fun hitLogin() {
//        viewModelScope.launch {
//
//            val mResp = async {
//                InjectorUtil.repository.loginAfterSignup(
//                        InjectorUtil.sessionNotNullGoMyWay().deviceFCMToken,
//                        InjectorUtil.sessionNotNullGoMyWay().emailRememberMe,
//                        InjectorUtil.sessionNotNullGoMyWay().pwdRememberMe)
//            }
//
//            // Send the value to the mutable data so that live data can be reflect on the views
//            mResponseLogin.postValue(mResp.await())
//
//        }
//    }


    fun hitLogin() {
        viewModelScope.launch {

            val mResp = async {
                InjectorUtil.repository.getEmailStatus(InjectorUtil.sessionNotNullGoMyWay().afterSignupToken)

            }

            // Send the value to the mutable data so that live data can be reflect on the views
            mResponseLogin.postValue(mResp.await())

        }
    }

    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(): LiveData<ModelCommon> = mResponse

    fun liveResponseLogin(): LiveData<ModelLogin> = mResponseLogin

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}