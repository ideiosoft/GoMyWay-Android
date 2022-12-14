package ssc.snow.app.gomyways.views.login.terms

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.otp_screen.img_cancel
import kotlinx.android.synthetic.main.terms_screen.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.data.utility.getHtmlText
import ssc.snow.app.gomyways.views.login.otp.RoundedBottomSheetDialogFragment


class TermsFragment : RoundedBottomSheetDialogFragment() {
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//      return MenuBinding.inflate(inflater, container, false).root
//    }

    lateinit var mViewModel: TermsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dialog?.setCanceledOnTouchOutside(false)

        // Inflate the layout for this fragment
        mViewModel = ViewModelProviders.of(this).get(TermsViewModel::class.java)

        return inflater.inflate(R.layout.terms_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // call api for terms
        mViewModel.getTerms()
        mViewModel.liveResponse().observe(viewLifecycleOwner, Observer {

            // Check for the null
            it?.let {
                if (it.status!!) {

                    // Get terms and conditions
                    txt_terms.text = getHtmlText(it.data?.description)

                } else {
                    InjectorUtil.showToast(it.message.toString())
                }
            }
        })

        img_cancel.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fragmentManager?.beginTransaction()?.detach(this)?.commitNow()
            } else {
                fragmentManager?.beginTransaction()?.detach(this)?.commit()
            }

        }


    }

}


