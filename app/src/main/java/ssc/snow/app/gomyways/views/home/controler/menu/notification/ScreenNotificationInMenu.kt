package ssc.snow.app.gomyways.views.home.controler.menu.notification

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_screen_notification_in_menu.*
import kotlinx.android.synthetic.main.toolbar_child.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.NotificationSettingsViewModel

class ScreenNotificationInMenu : CommonActivity() {


    private lateinit var mViewModel: NotificationSettingsViewModel

    private var mSms: String = "0"
    private var mEmail: String = "0"
    private var mPush: String = "0"

    private var mFlagUpdateSetting: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_notification_in_menu)

        // initialisations
        init()
        onClick()

    }

    private fun onClick() {

        img_back.setOnClickListener {
            finish()
        }

        btn_save_settings.setOnClickListener {

            // save the flag for the update the notification settings
            mFlagUpdateSetting = true

            // for the sms notifications
            if (sw_sms.isChecked)
                mSms = "1"
            else
                mSms = "0"

            // for push notifications
            if (sw_push_notification.isChecked)
                mPush = "1"
            else
                mPush = "0"


            // for emails notifications
            if (sw_product_emails.isChecked)
                mEmail = "1"
            else
                mEmail = "0"

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }


            // fire the save notifications
            mViewModel.saveNotificaionSettins(sessionInstance.loggedInUserDetail.data!!.user!!.token,
                    mSms, mEmail, mPush)

        }
    }

    private fun init() {
        txt_center_heading.text = resources.getString(R.string.notification_settings)

        // initialisation
        mViewModel = ViewModelProviders.of(this).get(NotificationSettingsViewModel::class.java)


        if (isNetworkConnected) {
            mViewModel.getNotifications(sessionInstance.loggedInUserDetail.data!!.user!!.token)

        }

        // live response for the notifications
        mViewModel.liveNotifications().observe(this, Observer {

            if (it != null) {
                if (it.status) {

                    // Setting the values to the switches
                    it.data?.let {

                        if (it.size > 0) {
                            it[0]?.let { it1 -> setAppropriateSwitch(it1.notification_type_id.toString(), it1.status.toString()) }
                            it[1]?.let { it1 -> setAppropriateSwitch(it1.notification_type_id.toString(), it1.status.toString()) }
                            it[2]?.let { it1 -> setAppropriateSwitch(it1.notification_type_id.toString(), it1.status.toString()) }

                        }


                    }


                    // show the dialog if uploaded new id for proof
                    if (mFlagUpdateSetting) {
                        successBox(it.message.toString())
                        // make it false for the next upload if prof
                        mFlagUpdateSetting = false
                    }

                } else {
                    showToast(it.message)
                }
            }
        })

    }


    private fun setAppropriateSwitch(mNotificationId: String, mStatus: String) {
        // for sms
        when (mNotificationId) {
            "1" -> sw_sms.isChecked = mStatus != "0"   // for sms
            "2" -> sw_product_emails.isChecked = mStatus != "0"  // for email
            "3" -> sw_push_notification.isChecked = mStatus != "0"  // for push

        }


    }


    // Show the success box to confirm user that data is updated
    fun successBox(msg: String) {
        SweetAlertDialog(this@ScreenNotificationInMenu, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(msg)
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                }
                .show()
    }

}
