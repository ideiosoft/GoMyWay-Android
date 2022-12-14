package ssc.snow.app.gomyways.viewmodel.my_trips

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import ssc.snow.app.gomyways.data.model.ModelCommon
import ssc.snow.app.gomyways.data.model.ModelPostTripDetail
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class ViewTripDetailViewModel : ViewModel() {

    var mTripDetails: MutableLiveData<ModelPostTripDetail?>
    var mTripOps: MutableLiveData<ModelCommon?>

    init {

        mTripDetails = MutableLiveData()
        mTripOps = MutableLiveData()

        // initialise mutable data to listen live data
        mTripDetails.postValue(null)
        mTripOps.postValue(null)


    }

    fun getViewTripDetails(mTripId: String) {
        viewModelScope.launch {

            val mResp = async {

                InjectorUtil.repository.viewTripDetail(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, mTripId)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mTripDetails.postValue(mResp.await())

        }
    }

    fun tripCloseOpen(mTripId: String, mTripStatus: String) {
        viewModelScope.launch {

            val mResp = async {

                InjectorUtil.repository.closeOpenTrip(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, mTripId, mTripStatus)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mTripOps.postValue(mResp.await())

        }
    }

    fun tripCancel(mTripId: String, mTripStatus: String) {
        viewModelScope.launch {

            val mResp = async {

                InjectorUtil.repository.cancelTrip(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, mTripId, mTripStatus)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mTripOps.postValue(mResp.await())

        }
    }

    fun tripMarkComplete(mTripId: String, mTripStatus: String) {
        viewModelScope.launch {

            val mResp = async {

                InjectorUtil.repository.markAsComplete(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, mTripId, mTripStatus)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mTripOps.postValue(mResp.await())

        }
    }


    fun getRoutesData(options: HashMap<String, String>?) = liveData(Dispatchers.IO) {
        emit(InjectorUtil.repositoryGoogle.getRoutes(options))

    }


    @BindingAdapter("imageUrl")
    fun setImageUrl(mView: ImageView, mUrl: String) {
        Glide.with(mView.context)
                .asBitmap()
                .load(mUrl)
                .into(mView)
    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveTripDetails(): LiveData<ModelPostTripDetail?> = mTripDetails

    fun liveTripOps(): LiveData<ModelCommon?> = mTripOps


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}