package ssc.snow.app.gomyways.views.home.controler.menu.wallet

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_screen_wallet.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil

class ScreenWallet : CommonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_wallet)


        // initialisations
        initViews()
    }

    private fun initViews() {
        if (isNetworkConnected) {
            initWebView()
        } else {
            showToast(resources.getString(R.string.provide_internet))
        }

        // put on Clicks
        img_back__.setOnClickListener {
            this@ScreenWallet.finish()
        }
    }


    private fun initWebView() {

        webview_wallet.settings?.apply {

            javaScriptEnabled = true
            allowContentAccess = true
            domStorageEnabled = false

            loadWithOverviewMode = true

            useWideViewPort = true

        }

        webview_wallet.loadUrl(InjectorUtil.WALLET_EARNING + sessionInstance.loggedInUserDetail.data!!.user?.token)

        // onclick specific event handling
        webview_wallet.webViewClient = webViewClient


    }

    val webViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.wtf("mUrl_s", "" + url)

//            if (url.equals(InjectorUtil.TRIP_SUBMIT_SUCCESSFULLY + sessionInstance.loggedInUserDetail.data!!.user!!.token, true)) {
//                goForHomeFromLeftToRight(ScreenHome::class.java)
//            }

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


}
