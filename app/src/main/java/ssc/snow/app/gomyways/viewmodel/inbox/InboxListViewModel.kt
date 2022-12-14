package ssc.snow.app.gomyways.viewmodel.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ssc.snow.app.gomyways.data.model.inbox.ModelInboxList
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class InboxListViewModel : ViewModel() {


    var mResponse: MutableLiveData<ModelInboxList?>

    init {
        mResponse = MutableLiveData()

        // initialise mutable data to listen live data
        mResponse.value = null

    }

    fun getInboxList() {
        viewModelScope.launch(Dispatchers.IO) {
            val mResp = async {
                InjectorUtil.repository.getInbox(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token)
            }
            // send the value to the mutable data so that live data can be reflect on the views
            mResponse.postValue(mResp.await())
        }
    }

    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(): LiveData<ModelInboxList?> = mResponse


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}