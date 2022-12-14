package ssc.snow.app.gomyways.views.home.controler.post_request

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_screen_edit_posted_trip.*
import kotlinx.android.synthetic.main.activity_screen_post_atrip.webview_post_trip
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.PostTripViewModel

class ScreenEditPostedTrip : CommonActivity() {

    lateinit var mViewModelPostTrip: PostTripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_edit_posted_trip)

        //   initialisations
        init()
        // initWebView()

    }

    private fun initWebView(it: String) {

        webview_post_trip.settings?.apply {

            javaScriptEnabled = true
            allowContentAccess = true
            domStorageEnabled = false

            loadWithOverviewMode = true

            useWideViewPort = true

        }

        webview_post_trip.loadUrl(it)
        // onclick specific event handling
        webview_post_trip.webViewClient = webViewClient


    }

    private fun init() {

        // Put on Clicks
        img_back.setOnClickListener {
            this@ScreenEditPostedTrip.finish()
        }

        // Post a trip view model th
        mViewModelPostTrip = ViewModelProviders.of(this).get(PostTripViewModel::class.java)

        // Call the api for the response to be checked
        if (isNetworkConnected) {
            mViewModelPostTrip.liveEditTripResponse().observe(this, Observer {

                it?.run {
                    initWebView(it)
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
                this@ScreenEditPostedTrip.finish()
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


}