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
import ssc.snow.app.gomyways.data.model.ModelIdVerification
import ssc.snow.app.gomyways.data.network.ApiRepository

class IdVerificationViewModel : ViewModel() {

    val mRepository: ApiRepository = ApiRepository()

    var mIdResponse: MutableLiveData<ModelIdVerification?>


    init {

        mIdResponse = MutableLiveData()

        // initialise mutable data to listen live data
        mIdResponse.postValue(null)

    }


    fun getAllIds(mToken: String) {
        viewModelScope.launch {

            val mResp = async {

                mRepository.getVerifedIds(mToken)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mIdResponse.postValue(mResp.await())

        }
    }

    fun saveuserIds(mToken: RequestBody, mFname: RequestBody, mLname: RequestBody, image: MultipartBody.Part) {
        viewModelScope.launch {
            val mResp = async {

                mRepository.saveUerIdentity(mToken, mFname, mLname, image)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mIdResponse.postValue(mResp.await())

        }
    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveIds(): LiveData<ModelIdVerification?> = mIdResponse


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}