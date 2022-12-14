package ssc.snow.app.gomyways.views.login.terms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ssc.snow.app.gomyways.data.model.ModelTerms
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class TermsViewModel : ViewModel() {

    var mResponse = MutableLiveData<ModelTerms>().apply {
        this.value = null
    }




    fun getTerms() {
        viewModelScope.launch {

            val mResp = async {

                InjectorUtil.repository.getTermsAndConditions()
            }

            // Send the value to the mutable data so that live data can be reflect on the views
            mResponse.postValue(mResp.await())

        }
    }

    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(): LiveData<ModelTerms?> = mResponse

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}