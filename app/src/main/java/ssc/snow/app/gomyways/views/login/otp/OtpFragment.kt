package ssc.snow.app.gomyways.views.login.otp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.otp_screen.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.views.MyApplication
import ssc.snow.app.gomyways.views.home.ScreenHome
import ssc.snow.app.gomyways.views.login.otp.viewmodel.OTPVerifiyViewModel


class OtpFragment : RoundedBottomSheetDialogFragment() {
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//      return MenuBinding.inflate(inflater, container, false).root
//    }

    lateinit var mViewModel: OTPVerifiyViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
             container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dialog?.setCanceledOnTouchOutside(false)

        // Inflate the layout for this fragment
        mViewModel = ViewModelProviders.of(this).get(OTPVerifiyViewModel::class.java)

        return inflater.inflate(R.layout.otp_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observer
        mViewModel.liveResponsePhoneNumber().observe(viewLifecycleOwner, Observer {
            // Check for the null
            txt_phone_number.text = it

        })

        mViewModel.liveResponse().observe(viewLifecycleOwner, Observer {

            // Check for the null
            it?.let {
                if (it.status) {


                    // Store user details
                    InjectorUtil.sessionNotNullGoMyWay().isAfterSignup = false
                    InjectorUtil.sessionGoMyWay().isLoggedIn = true
                    InjectorUtil.sessionGoMyWay().setMobileStatus("1")


                    //  goForNextScreen(ScreenHome::class.java)
                    val mIntent = Intent(MyApplication.activityContext(), ScreenHome::class.java)
                    mIntent.let {
                        startActivity(mIntent)
                    }
                    // finish the activity
                    MyApplication.activityContext().finish()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        fragmentManager?.beginTransaction()?.detach(this)?.commitNow()
                    } else {
                        fragmentManager?.beginTransaction()?.detach(this)?.commit()
                    }


                } else {
                    InjectorUtil.showToast(it.message.toString())
                }
            }


        })

        // phone_no_et
        mViewModel.liveResponse().observe(this, Observer {
            it?.let {

                if (it.status) {
                    // show Message short
                    InjectorUtil.showToast(it.message.toString())
                } else
                    InjectorUtil.showToast(it.message.toString())

            }


        })

        txt_Resend.setOnClickListener {

            if (!InjectorUtil.isNetworkConnected()) {
                InjectorUtil.showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }

            //   InjectorUtil.mPhoneNumber = phone_no_et.text.toString()
            mViewModel.phoneVerification(
                    InjectorUtil.mPhoneNumber,
                    InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token)


        }


        btn_verify.setOnClickListener {


            // Save the phone number for the otp page to show there
            if (pinview_otp.text.isNullOrBlank()) {
                //    show("Please provide valid number")
                return@setOnClickListener

            } else {

                if (!InjectorUtil.isNetworkConnected()) {
                    return@setOnClickListener
                }

                mViewModel.confirmCode(pinview_otp.text.toString(), InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token)
            }


        }

        txt_change_phone.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fragmentManager?.beginTransaction()?.detach(this)?.commitNow()
            } else {
                fragmentManager?.beginTransaction()?.detach(this)?.commit()
            }

        }

        img_cancel.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fragmentManager?.beginTransaction()?.detach(this)?.commitNow()
            } else {
                fragmentManager?.beginTransaction()?.detach(this)?.commit()
            }

        }


    }

}


