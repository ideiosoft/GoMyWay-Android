package ssc.snow.app.gomyways.views.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_logged_in_after_signup.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import java.util.*

class ActivityLoggedInAfterSignup : CommonActivity() {


    lateinit var mViewModel: ImmediateAfterSignupViewModel


    var timer: Timer? = null
    var timerTask: TimerTask? = null
    //we are going to use a handler to be able to run in our TimerTask
    val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in_after_signup)


        // initialisations
        init()
        onClicks()
    }

    override fun onStart() {
        super.onStart()
        // start timer for the continously
        startTimer()

//        if (!isNetworkConnected) {
//            showToast(resources.getString(R.string.provide_internet))
//            return
//        }
//
//        //  get the current timeStamp
//        mViewModel.hitLogin()
    }

    private fun onClicks() {

        txt_logout.setOnClickListener {

            InjectorUtil.sessionNotNullGoMyWay().isAfterSignup = false
            goForHomeFromLeftToRight(ScreenLogin::class.java)
        }


        btn_send_code.setOnClickListener {
            // check for the network
            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }

            mViewModel.resendEmailLink()
        }

    }


    @SuppressLint("SetTextI18n")
    private fun init() {
        // set the value to the textview
        txt_need_to_verified.text = "Email Verification sent successfully to your email ${sessionInstanceNotNull.emailRememberMe}"

        // initialisations
        mViewModel = ViewModelProviders.of(this).get(ImmediateAfterSignupViewModel::class.java)

        mViewModel.liveResponse().observe(this, androidx.lifecycle.Observer {

            it?.let {
                if (it.status) {
                    showMesageAfterResentLink(it.message)
                }
            }
        })

        mViewModel.liveResponseLogin().observe(this, androidx.lifecycle.Observer {

            it?.let {
                if (it.status!!) {

                    // Store user details
                    sessionInstance.storeLoggedInUserDetail(it)

                    // if email is not verified
                    if (sessionInstance.loggedInUserDetail.data!!.user!!.email_status.equals("1", ignoreCase = true)) {
                        if (sessionInstance.loggedInUserDetail.data!!.user!!.mobile_status.equals("0", ignoreCase = true)) {

                            // send the control to the next screen for the mobile screen verification
                            goForNextScreen(ScreenEnterMobileNumber::class.java)
                        }
                    }
                }


            }


        })


    }


    override fun onRestart() {
        super.onRestart()
        Log.wtf("restart", "Restarted called")
    }


    override fun onBackPressed() {
        super.onBackPressed()
        // finish all the screens
        finishAffinity()
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

                    //  get the current timeStamp
                    mViewModel.hitLogin()

                }
            }
        }
    }


    fun showMesageAfterResentLink(msg: String?) {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(Html.fromHtml(msg).toString())
                .setConfirmText("Ok")
                .setConfirmClickListener { sDialog -> sDialog.dismissWithAnimation() }
                .show()
    }


}
