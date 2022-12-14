package ssc.snow.app.gomyways.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.*
import ssc.snow.app.gomyways.data.model.ModelSavedBankCards
import ssc.snow.app.gomyways.data.network.ApiRepository
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class BankDetailsViewModel : ViewModel() {

    val mRepository: ApiRepository = ApiRepository()

    private var mResponsCards = MutableLiveData<ModelSavedBankCards?>().apply {
        value = null

    }

    /***
     * Calling methods for the api operations
     * ***/


    fun getBankDetail(mToken: String) {
        viewModelScope.launch {

            val mResp = async {

                mRepository.getAllSavedCards(mToken)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mResponsCards.postValue(mResp.await())

        }
    }

//    fun delBankCard(mToken: String, mCardId: String) {
//        viewModelScope.launch {
//
//            val mResp = async {
//
//                mRepository.deleteBankCard(mToken, mCardId)
//            }
//
//            // send the value to the mutable data so that live data can be reflect on the views
//            mResponsCards.postValue(mResp.await())
//
//        }
//    }


    fun addBankAccountNumber(mToken: String, mAccNo: String, bank_user_name: String, bank_name: String) {
        viewModelScope.launch {
            val mResp = async {

                mRepository.addBankCard(mToken, mAccNo, bank_user_name,bank_name)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mResponsCards.postValue(mResp.await())

        }
    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveCardResponse(): LiveData<ModelSavedBankCards?> = mResponsCards

    //    // start coroutine decalaration and implementation
//    var mLiveMonths = liveData(Dispatchers.IO) {
//        emit(getMonthsOfYear())
//    }
//
//    // start coroutine decalaration and implementation
//    var mLiveYears = liveData(Dispatchers.IO) {
//        emit(getYears())
//
//    }
//
    // start coroutine decalaration and implementation for card types
    var mLiveBankNames = liveData(Dispatchers.IO) {
        emit(mRepository.getBankNames(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token))

    }


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}