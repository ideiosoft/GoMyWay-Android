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
import ssc.snow.app.gomyways.data.model.ModelGetAllEmails
import ssc.snow.app.gomyways.data.network.ApiRepository

class EmailViewModel : ViewModel() {

    val mRepository: ApiRepository = ApiRepository()

    var mGetAllEmails: MutableLiveData<ModelGetAllEmails?>
    var mUserEmailResponse: MutableLiveData<ModelCommon?>
    //  var mVehicleTypes: MutableLiveData<ModelVehicleAndTypes?>


    init {

        mGetAllEmails = MutableLiveData()
        mUserEmailResponse = MutableLiveData()
        //   mVehicleTypes = MutableLiveData()


        mGetAllEmails.postValue(null)
        mUserEmailResponse.postValue(null)
        //   mVehicleTypes.postValue(null)

    }


    fun getAllEmails(mToken: String) {
        viewModelScope.launch {

            val mResp = async {

                mRepository.getAllAssociat_emails(mToken)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mGetAllEmails.postValue(mResp.await())

        }
    }

    fun saveUserEmail(mToken: String, mEmails: String) {
        viewModelScope.launch {
            val mResp = async {

                mRepository.addUser_email(mToken, mEmails)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mUserEmailResponse.postValue(mResp.await())

        }


    }

    fun deleteAndMakePrimaryEmail(mToken: String, mEmails: String, mType: String) {
        viewModelScope.launch {
            val mResp = async {

                mRepository.deleteAndMakePrimaryUserEmail(mToken, mEmails, mType)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mUserEmailResponse.postValue(mResp.await())

        }


    }

    fun resendEmailVerification(mToken: String, mEmails: String, mEmailType: String) {
        viewModelScope.launch {
            val mResp = async {

                mRepository.resendVerification_email(mToken, mEmails, "verification", mEmailType)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mUserEmailResponse.postValue(mResp.await())

        }


    }

    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveAllEmails(): LiveData<ModelGetAllEmails?> = mGetAllEmails

    fun liveEmailRespnses(): LiveData<ModelCommon?> = mUserEmailResponse
    // fun liveVehiclesAndTypes(): LiveData<ModelVehicleAndTypes?> = mVehicleTypes


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}