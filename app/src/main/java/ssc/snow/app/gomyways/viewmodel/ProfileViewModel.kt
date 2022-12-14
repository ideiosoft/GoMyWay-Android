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
import ssc.snow.app.gomyways.data.model.ModelLogin
import ssc.snow.app.gomyways.data.model.ModelStates
import ssc.snow.app.gomyways.data.model.ModelUserVehicles
import ssc.snow.app.gomyways.data.model.ModelVehicleAndTypes
import ssc.snow.app.gomyways.data.network.ApiRepository
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class ProfileViewModel : ViewModel() {

    val mRepository: ApiRepository = ApiRepository()

    var mUpdateProfileResp: MutableLiveData<ModelLogin?> = MutableLiveData()
    var mUserVehicles: MutableLiveData<ModelUserVehicles?> = MutableLiveData()
    var mVehicleTypes: MutableLiveData<ModelVehicleAndTypes?> = MutableLiveData()
    var mStateNames = MutableLiveData<ModelStates?>().apply {
        getStates()
    }


    init {

        mUpdateProfileResp.postValue(null)
        mUserVehicles.postValue(null)
        mVehicleTypes.postValue(null)

    }

    /***
     * Calling methods for the api operations
     * ***/
    fun updateProfile(mHno: RequestBody, mStreet: RequestBody, mCityName: RequestBody, mStateName: RequestBody, mToken: RequestBody, mFname: RequestBody, mLname: RequestBody, mPhoneNo: RequestBody, mDOB: RequestBody, mAbout: RequestBody, mGender: RequestBody, mPayStack: RequestBody, userType: RequestBody, image: MultipartBody.Part) {
        viewModelScope.launch {

            val mResp = async {
                mRepository.updateProfile(mHno, mStreet, mCityName, mStateName, mToken, mFname, mLname, mPhoneNo, mDOB, mAbout, mGender, mPayStack, userType, image)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mUpdateProfileResp.postValue(mResp.await())

        }


    }

    private fun getStates() {


        viewModelScope.launch {

            val mResp = async {
                mRepository.getStates(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mStateNames.postValue(mResp.await())

        }
    }

    fun getUserVehicles(mToken: String) {
        viewModelScope.launch {

            val mResp = async {

                mRepository.getUserVehicles(mToken)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mUserVehicles.postValue(mResp.await())

        }
    }

    fun addUserVehicle(mToken: RequestBody, vehicleId: RequestBody, vehicleTypeId: RequestBody,
                       mModel: RequestBody, mPlateNo: RequestBody, image: ArrayList<MultipartBody.Part>) {
        viewModelScope.launch {

            val mResp = async {

                mRepository.addUserVehicle(mToken, vehicleId, vehicleTypeId, mModel, mPlateNo, image)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mUserVehicles.postValue(mResp.await())

        }


    }

    fun delUserVehicle(mToken: String, mVehicleId: String) {
        viewModelScope.launch {

            val mResp = async {

                mRepository.delUserVehicle(mToken, mVehicleId)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mUserVehicles.postValue(mResp.await())

        }


    }

    fun getVehiclesAndTypes(mToken: String) {
        viewModelScope.launch {

            val mResp = async {

                mRepository.getVehiclesAndTypes(mToken)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mVehicleTypes.postValue(mResp.await())

        }


    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveUpdateRespone(): LiveData<ModelLogin?> = mUpdateProfileResp

    fun liveUserVehicles(): LiveData<ModelUserVehicles?> = mUserVehicles
    fun liveVehiclesAndTypes(): LiveData<ModelVehicleAndTypes?> = mVehicleTypes
    fun liveStatesResponse(): LiveData<ModelStates?> = mStateNames


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}