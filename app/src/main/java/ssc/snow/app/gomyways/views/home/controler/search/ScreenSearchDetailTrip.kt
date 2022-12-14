package ssc.snow.app.gomyways.views.home.controler.search

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_screen_edit_posted_trip.img_back
import kotlinx.android.synthetic.main.activity_screen_search_detail_trip.*
import kotlinx.android.synthetic.main.layout_item_rides.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.SearchedTripViewModel
import ssc.snow.app.gomyways.views.home.ScreenHome


class ScreenSearchDetailTrip : CommonActivity() {

    lateinit var mViewModelDetail: SearchedTripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_search_detail_trip)

        //   initialisations
        init()
        // initWebView()

    }

    private fun initWebView(it: String) {

        webview_trip_detail.settings?.apply {

            javaScriptEnabled = true
            allowContentAccess = true
            setAppCacheEnabled(true)
            domStorageEnabled = true

            loadWithOverviewMode = true
            useWideViewPort = true

            if (Build.VERSION.SDK_INT >= 21) {
                // Log.d(AppConstants.TAG,"SDk version above android L so forcefully enabling ThirdPartyCookies")
               //  mixedContentMode
                CookieManager.getInstance().setAcceptThirdPartyCookies(webview_trip_detail, true)
              //  CookieHandler.setDefault(CookieManager())
            }

        }

        //  CookieManager.getInstance().setAcceptCookie(true)

        webview_trip_detail.loadUrl(it)
        // onclick specific event handling
        webview_trip_detail.webViewClient = webViewClient


    }

    private fun init() {

        // Put on Clicks
        img_back.setOnClickListener {
            this@ScreenSearchDetailTrip.finish()
        }

        // Post a trip view model th
        mViewModelDetail = ViewModelProviders.of(this).get(SearchedTripViewModel::class.java)

        // Call the api for the response to be checked
        if (isNetworkConnected) {
            mViewModelDetail.liveSearchedTripResponse().observe(this, Observer {
                it.run {
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

            if (url.toString().startsWith(InjectorUtil.BOOKED_TRIP_CONGRATESS)) {
                goForHomeFromLeftToRight(ScreenHome::class.java)
            }

           /*if (url.equals(InjectorUtil.BOOKED_TRIP_CONGRATES.replace("LOGGED_TOKEN", sessionInstance.loggedInUserDetail.data!!.user!!.token), true)) {
                goForHomeFromLeftToRight(ScreenHome::class.java)
            }*/
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