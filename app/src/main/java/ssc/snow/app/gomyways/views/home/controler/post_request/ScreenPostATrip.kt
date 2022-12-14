package ssc.snow.app.gomyways.views.home.controler.post_request

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_screen_post_atrip.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.PostTripViewModel
import ssc.snow.app.gomyways.views.home.ScreenHome


class ScreenPostATrip : CommonActivity() {

    private lateinit var mViewModelPostTrip: PostTripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_post_atrip)

        //   initialisations
        init()
        //   initWebView()
        //   initWebView()

    }

    private fun initWebView() {

        webview_post_trip.settings?.apply {

            javaScriptEnabled = true
            allowContentAccess = true
            domStorageEnabled = false

            loadWithOverviewMode = true

            useWideViewPort = true

        }

        webview_post_trip.loadUrl("${InjectorUtil.POST_TRIP_URL + sessionInstance.loggedInUserDetail.data!!.user?.token}")

        // onclick specific event handling
        webview_post_trip.webViewClient = webViewClient


    }

    private fun init() {
        // Post a trip view model th
        mViewModelPostTrip = ViewModelProviders.of(this).get(PostTripViewModel::class.java)

        // put on Clicks
        img_back__.setOnClickListener {
            this@ScreenPostATrip.finish()
        }

        // Call the api for the response to be checked
        if (isNetworkConnected) {
            mViewModelPostTrip.liveResponse(sessionInstance.loggedInUserDetail.data!!.user!!.token)
                    .observe(this, Observer {

                        if (it != null) {
                            if (it.status) {
                                // Call the web view functionality
                                initWebView()
                            } else {
                                warning(it.message.toString())

                            }
                        }
                    })
        } else {
            showToast(resources.getString(R.string.provide_internet))
            return
        }
    }

    val webViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.wtf("mUrl_s", "" + url)

            if (url.equals(InjectorUtil.TRIP_SUBMIT_SUCCESSFULLY + sessionInstance.loggedInUserDetail.data!!.user!!.token, true)) {
                goForHomeFromLeftToRight(ScreenHome::class.java)
            }

        }

        override fun shouldOverrideUrlLoading(view: WebView, request: String): Boolean {
            try {
                Log.wtf("mUrl_l", "" + request)

                // Do Whatever you want to do on a web link click
                // http://112.196.5.115/go_my_ways/?token=f88ab5040d220fa2dda3785744dc9e49

                view.loadUrl(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()

    }

//   private fun warning(msg: String) {
//        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setContentText(Html.fromHtml(msg).toString())
//                .showContentText(true)
//                .setConfirmText(resources.getString(string.ok))
//                .setConfirmClickListener { sDialog ->
//                    sDialog.dismissWithAnimation()
//                    this@ScreenPostATrip.finish()
//
//
//                }
//                .show()
//    }

 private fun warning(msg: String) {
     val builder = AlertDialog.Builder(this)
     //set title for alert dialog
     builder.setTitle("Warning")
     //set message for alert dialog
     builder.setMessage(msg)
     builder.setIcon(R.mipmap.app_icon_48)

     //performing positive action
     builder.setPositiveButton("OK"){dialogInterface, which ->
         this@ScreenPostATrip.finish()
     }


     // Create the AlertDialog
     val alertDialog: AlertDialog = builder.create()
     // Set other dialog properties
     alertDialog.setCancelable(true)
     alertDialog.show()


 }


}
