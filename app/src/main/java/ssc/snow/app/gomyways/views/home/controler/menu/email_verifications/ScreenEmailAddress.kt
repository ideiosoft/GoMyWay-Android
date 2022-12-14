package ssc.snow.app.gomyways.views.home.controler.menu.email_verifications

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_screen_email_address.*
import kotlinx.android.synthetic.main.toolbar_email.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelGetAllEmails
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.EmailViewModel
import ssc.snow.app.gomyways.views.home.adapter.AdapterEmails
import ssc.snow.app.gomyways.views.home.controler.profile.IEmailCallback

class ScreenEmailAddress : CommonActivity() {


    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapterEmail: AdapterEmails

    private lateinit var mViewModel: EmailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_email_address)


        initOnClick()
        initilisation()
    }

    private fun initOnClick() {
        img_back.setOnClickListener {
            finish()
        }

        btn_add_another_email.setOnClickListener {

            if (edt_add_email.isShown) {
                edt_add_email.visibility = View.GONE
                btn_add_email.visibility = View.GONE

                // set text accordingly
                btn_add_another_email.text = resources.getString(R.string.add_another_email)

            } else {
                edt_add_email.visibility = View.VISIBLE
                btn_add_email.visibility = View.VISIBLE

                // set text accordingly
                btn_add_another_email.text = resources.getString(R.string.hide)
            }


        }

        // Submit the email to the server
        btn_add_email.setOnClickListener {

            if (edt_add_email.text.isBlank()) {
                edt_add_email.error = resources.getString(R.string.field_cnt_empty)
                return@setOnClickListener
            }

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }
            // fire the api for the save user email
            mViewModel.saveUserEmail(sessionInstance.loggedInUserDetail.data!!.user!!.token, edt_add_email.text.toString())

        }
        // Submit the email to the server
        txt_resend_verifications.setOnClickListener {

            if (txt_email_primary.text.isBlank()) {
                return@setOnClickListener
            }

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }

            // fire the api for the save user email
            mViewModel.resendEmailVerification(sessionInstance.loggedInUserDetail.data!!.user!!.token, edt_add_email.text.toString(), "p")

        }

    }

    private fun initilisation() {

        mViewModel = ViewModelProviders.of(this).get(EmailViewModel::class.java)

        // Set layout manager to the recycler view
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycle_emails.layoutManager = layoutManager

        if (isNetworkConnected) {
            mViewModel.getAllEmails(sessionInstance.loggedInUserDetail.data!!.user!!.token)
        }

        mViewModel.liveAllEmails().observe(this, Observer {

            if (it != null) {
                if (it.status) {

                    // set the values to the views
                    txt_email_primary.text = "\u25CF ${it.data!!.primary_email!!.email}"
                    val mVerify = when (it.data.primary_email!!.email_status) {
                        "0" -> {
                            "Unverified"
                        }
                        else -> {
                            "Verified"
                        }
                    }

                    // set email status text
                    txt_verified.text = mVerify

                    // set recycler view
                    setRecyclerView(recycle_emails, it.data.other_emails)
                } else {
                    showToast(it.message)
                }
            }
        })

        // observe the data for the operation on the emails i.e del, makeprimary, resend verification,
        mViewModel.liveEmailRespnses().observe(this, Observer {

            // dismiss the progress dialog
            dismissIOSProgress()


            if (it != null) {
                if (it.status) {
                    successBox(it.message.toString())
                } else {
                    warningBox(it.message)
                }
            }


        })

    }

    // set recycler view for the upcoming trips
    private fun setRecyclerView(mRecycleSearch: RecyclerView, mListData: List<ModelGetAllEmails.Data.OtherEmail?>?) {

        applicationContext?.let {
            mAdapterEmail = AdapterEmails(applicationContext, mListData, mCallback)
            // Set adapter on the recyclerview
            mRecycleSearch.adapter = mAdapterEmail
        }


    }


    // callback for the operations on the list

    val mCallback = object : IEmailCallback {

        override fun delUserEmail(mEmail: String) {

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return
            }

            // Confirm dialog
            confirmDelete("Do you really want to Delete?", mEmail)
        }

        override fun resendVerificationEmail(mEmail: String) {
            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return
            }
            // show progress when firing api
            showIOSProgress()
            mViewModel.resendEmailVerification(sessionInstance.loggedInUserDetail.data!!.user!!.token, mEmail, "")


        }

        override fun makePrimaryEmail(mEmail: String, mEmailType: String) {
            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return
            }
            // show progress when firing api
            showIOSProgress()
            mViewModel.deleteAndMakePrimaryEmail(sessionInstance.loggedInUserDetail.data!!.user!!.token, mEmail, "primary")


        }

    }

    // Show the success box to confirm user that data is updated
    fun successBox(msg: String) {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(msg)
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                    refreshScreen(this@ScreenEmailAddress)
                }
                .show()
    }


    fun confirmDelete(msg: String?, mEmail: String) {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Confirmation")
                .setContentText(Html.fromHtml(msg).toString())
                .setConfirmButton("Confirm") { sDialog ->
                    sDialog.dismissWithAnimation()

                    if (!isNetworkConnected) {
                        showToast(resources.getString(R.string.provide_internet))

                    } else {

                        // Show progress when firing api
                        showIOSProgress()
                        mViewModel.deleteAndMakePrimaryEmail(
                                sessionInstance.loggedInUserDetail.data!!.user!!.token,
                                mEmail,
                                "delete")
                    }
                }
                .setCancelButton("Cancel") { sDialog ->
                    sDialog.dismissWithAnimation()
                }
                .show()
    }

}
