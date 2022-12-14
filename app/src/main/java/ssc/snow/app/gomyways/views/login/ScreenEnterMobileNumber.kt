package ssc.snow.app.gomyways.views.login

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_screen_enter_mobile_number.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.views.login.otp.OtpFragment
import ssc.snow.app.gomyways.views.login.otp.viewmodel.MobileNumberVerifyViewModel


class ScreenEnterMobileNumber : CommonActivity(), CountryCodePicker.OnCountryChangeListener {

    lateinit var mViewModel: MobileNumberVerifyViewModel
    private var countryCode: String? = null
    private var countryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_enter_mobile_number)

        // initialisations
        init()

    }

    private fun init() {

//        country_code_picker!!.setOnCountryChangeListener(this)
        //to set default country code as Japan
        //  country_code_picker!!.setDefaultCountryUsingNameCode("NG")

        // initialise the view model
        mViewModel = ViewModelProviders.of(this).get(MobileNumberVerifyViewModel::class.java)
        // phone_no_et
        mViewModel.liveResponse().observe(this, Observer {
            it?.let {

                if (it.status) {
                    // Value for the otp fragment to fill in that
                    val menuFragment = OtpFragment()
                    menuFragment.show(supportFragmentManager, menuFragment.tag)

                    // show Message short
                    showToast(it.message)
                } else
                    showToast(it.message)

            }


        })



        btn_send_code.setOnClickListener {

//            KmConversationBuilder(ScreenEnterMobileNumber@ this)
//                    .launchConversation(object : KmCallback {
//                        override fun onSuccess(message: Any) {
//                            Log.d("Conversation", "Success : $message")
//                        }
//
//                        override fun onFailure(error: Any) {
//                            Log.d("Conversation", "Failure : $error")
//                        }
//                    })

            // Save the phone number for the otp page to show there
            if (phone_no_et.text.isNullOrBlank()) {
                showToast("Please provide valid number")
                return@setOnClickListener

            } else {

//               InjectorUtil.mPhoneNumber = "${country_code_picker!!.selectedCountryCodeWithPlus}${phone_no_et.text}"

             //   InjectorUtil.mPhoneNumber = phone_no_et.text.toString()
                mViewModel.phoneVerification(InjectorUtil.mPhoneNumber, sessionInstance.loggedInUserDetail.data!!.user!!.token)

            }


        }

    }

    override fun onCountrySelected() {

//        countryCode = country_code_picker!!.selectedCountryCodeWithPlus
//        countryName = country_code_picker!!.selectedCountryName

        Log.wtf("ccp", "${countryCode} - ${countryName}")


    }


}
