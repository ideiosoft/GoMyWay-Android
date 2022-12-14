package ssc.snow.app.gomyways.views.home.controler

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_screen_inbox_detail.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.inbox.Message
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.inbox.InboxViewModel
import ssc.snow.app.gomyways.views.home.adapter.AdapterChat
import java.util.*

class ScreenInboxDetail : CommonActivity() {


    private var mAdapterChat: AdapterChat? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    lateinit var mInboxViewModel: InboxViewModel

    var mListMessages = arrayListOf<Message?>()


    var timer: Timer? = null
    var timerTask: TimerTask? = null
    //we are going to use a handler to be able to run in our TimerTask
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_inbox_detail)

        // initialisations
        init()
        onClick()
    }

    private fun onClick() {

        img_back.setOnClickListener {

            this@ScreenInboxDetail.finish()
        }

        btn_send.setOnClickListener {

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }

            if (edt_message_.text.toString().isNotEmpty()) {
                // mInboxViewModel.send
                mInboxViewModel.sendMessage(edt_message_.text.toString().trim())
                edt_message_.setText("")
            }
        }

    }

    private fun init() {
        // init view model
        mInboxViewModel = ViewModelProviders.of(this).get(InboxViewModel::class.java)

        // To prevent layout from going top
        recycle_chat!!.isFocusable = false
        recycle_chat!!.requestFocus()
        mLinearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycle_chat!!.layoutManager = mLinearLayoutManager

        recycle_chat.addOnLayoutChangeListener { v, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                recycle_chat.postDelayed({ recycle_chat.smoothScrollToPosition(mAdapterChat!!.itemCount) }, 100)
            }
        }


//        recycle_chat.OnEditorActionListener(OnEditorActionListener { v, actionId, event ->
//            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
//                if (!TextUtils.isEmpty(edtMessage.getText().toString().trim() {
//                    sendMessage(edtMessage.getText().toString().trim())
//                    recycle_chat.setText("")
//                }
//            }
//            true
//        })
//

        // Get all chat
        if (isNetworkConnected) {
            mInboxViewModel.getAllChat()
        }

        // Observer
        mInboxViewModel.liveAllChatRespone().observe(this, Observer {
            if (it != null) {
                if (it.status!!) {
                    mListMessages.clear()
                    mListMessages = it.data!!
                    initRecycler(mListMessages)
                    // start timer for receiving data
                    startTimer()

                } else
                    showToast(it.message)
            }
        })

        mInboxViewModel.liveRecentRespone().observe(this, Observer {
            if (it != null) {
                if (it.status!!) {

                    mListMessages.addAll(it.data!!)
                    mAdapterChat?.notifyDataSetChanged()
                    recycle_chat.smoothScrollToPosition(mAdapterChat!!.itemCount)

                } else
                    showToast(it.message)
            }
        })

        mInboxViewModel.liveSendRespone().observe(this, Observer {
            if (it != null) {
                if (it.status!!) {
                    mListMessages.add(it.data!!)

                    mAdapterChat?.notifyDataSetChanged()
                    recycle_chat.smoothScrollToPosition(mAdapterChat!!.itemCount)
                } else
                    showToast(it.message)
            }
        })

    }

    fun initRecycler(mListMessages: ArrayList<Message?>) {
        // set adapter
        mAdapterChat = AdapterChat(this, mListMessages)
        recycle_chat!!.adapter = mAdapterChat
        // scroll to smoth position
        // scroll to smoth position
        recycle_chat.smoothScrollToPosition(mAdapterChat!!.itemCount)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this@ScreenInboxDetail.finish()
    }

    override fun onStop() {
        super.onStop()
        stoptimertask()
    }

    fun startTimer() { //set a new Timer
        timer = Timer()
        //initialize the TimerTask's job
        initializeTimerTask()
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer!!.schedule(timerTask, 1000, 3000) //
    }

    fun stoptimertask() { //stop the timer, if it's not already null
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    fun initializeTimerTask() {
        timerTask = object : TimerTask() {
            override fun run() { //use a handler to run a toast that shows the current timestamp
                handler.post {

                    if (!isNetworkConnected) {
                        showToast(resources.getString(R.string.provide_internet))
                        return@post
                    }

                    //get the current timeStamp
                    mInboxViewModel.getRecentMessage()
                }
            }
        }
    }

}