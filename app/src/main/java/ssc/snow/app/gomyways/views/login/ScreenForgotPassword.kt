package ssc.snow.app.gomyways.views.login

import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_screen_forgot_password.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.ForgotPasswordViewModel

class ScreenForgotPassword : CommonActivity() {

    lateinit var mViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_forgot_password)
        ButterKnife.inject(this)

        init()
    }


    private fun init() {
        // initialise the view model
        mViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)
        mViewModel.liveResponse().observe(this, Observer {

            if (it != null) {
                if (it.status)
                    successBox(it.message)
                else
                    warn(it.message.toString())
            }
        })



        reset_btn.setOnClickListener {

           //  goForNextScreenWithoutFinish(ScreenEnterMobileNumber::class.java)

            if (TextUtils.isEmpty(email_et.text.toString())) {
                email_et.error = "Please provide correct email"
                return@setOnClickListener
            }

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }
           // fire the api
            mViewModel.forgotPassword(email_et.text.toString())


        }

    }

    fun successBox(msg: String?) {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(msg)
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                    goForHomeFromLeftToRight(ScreenLogin::class.java)
                }
                .show()
    }

    fun warn(msg: String) {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText(msg)
                .setConfirmClickListener { sDialog -> sDialog.dismissWithAnimation() }
                .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}