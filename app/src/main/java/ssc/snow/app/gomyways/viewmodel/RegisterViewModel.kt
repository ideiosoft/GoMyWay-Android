package ssc.snow.app.gomyways.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ssc.snow.app.gomyways.data.model.ModelAfterSignup
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class RegisterViewModel : ViewModel() {


    private var mRegisterMutable = MutableLiveData<ModelAfterSignup>().apply {
        value = null
    }

    /***
     * Calling methods for the api operations
     * ***/
    fun registerHIt(terms_conditions: RequestBody, mFname: RequestBody, mLname: RequestBody, username: RequestBody,
                    mDOB: RequestBody, mEmail: RequestBody, mPass: RequestBody, mGender: RequestBody,
                    userType: RequestBody, profile_photo: MultipartBody.Part, id_photo: MultipartBody.Part) {
        viewModelScope.launch {

            val mResp = async {
                InjectorUtil.repository.register(terms_conditions,mFname,mLname,username,mDOB,mEmail,
                        mPass,mGender,userType,profile_photo,id_photo)

            }

            // send the value to the mutable data so that live data can be reflect on the views
            mRegisterMutable.postValue(mResp.await())

        }


    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveRegisterRespone(): LiveData<ModelAfterSignup?> = mRegisterMutable


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}