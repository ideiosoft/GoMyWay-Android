package ssc.snow.app.gomyways.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import ssc.snow.app.gomyways.data.network.ApiRepository

class AboutUsViewModel : ViewModel() {

    val mRepository: ApiRepository = ApiRepository()


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveResponse(mToken: String) = liveData(Dispatchers.IO) {

        emit(mRepository.getAboutUs(mToken))

    }


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}