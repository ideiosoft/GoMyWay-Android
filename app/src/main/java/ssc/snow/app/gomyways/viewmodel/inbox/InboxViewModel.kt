package ssc.snow.app.gomyways.viewmodel.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ssc.snow.app.gomyways.data.model.inbox.ModelAllChat
import ssc.snow.app.gomyways.data.model.inbox.ModelSendMessage
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class InboxViewModel : ViewModel() {

    var mAllChat: MutableLiveData<ModelAllChat?>
    var mRecentMessages: MutableLiveData<ModelAllChat?>

    var mSendMsgResponse: MutableLiveData<ModelSendMessage?>


    init {

        mAllChat = MutableLiveData()
        mRecentMessages = MutableLiveData()
        mSendMsgResponse = MutableLiveData()

        mAllChat.postValue(null)
        mRecentMessages.postValue(null)
        mSendMsgResponse.postValue(null)

    }


    /***
     * Calling methods for the api operations
     * ***/
    fun getAllChat() {
        viewModelScope.launch(Dispatchers.IO) {
            val mResp = async {
                InjectorUtil.repository.getAllChat(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, InjectorUtil.mConversationId)
            }

            // Send the value to the mutable data so that live data can be reflect on the views
            mAllChat.postValue(mResp.await())

        }


    }


    fun getRecentMessage() {

        viewModelScope.launch {
            val mResp = async(Dispatchers.IO) {
                InjectorUtil.repository.getRecentMessage(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token,
                        InjectorUtil.mConversationId)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mRecentMessages.postValue(mResp.await())

        }
    }


    fun sendMessage(mMsg: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val mResp = async {

                InjectorUtil.repository.sendMessage(InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token,
                        mMsg, InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.id.toString(),
                        InjectorUtil.mToId, InjectorUtil.mConversationId)
            }

            // send the value to the mutable data so that live data can be reflect on the views
            mSendMsgResponse.postValue(mResp.await())

        }


    }


    /***
     * Live data observer for the views independent operation from the UI
     * ***/
    fun liveAllChatRespone(): LiveData<ModelAllChat?> = mAllChat
    fun liveRecentRespone(): LiveData<ModelAllChat?> = mRecentMessages

    fun liveSendRespone(): LiveData<ModelSendMessage?> = mSendMsgResponse


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}