package ssc.snow.app.gomyways.views.home.controler.menu.about

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_screen_about.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.AboutUsViewModel

class ScreenAbout : CommonActivity() {


    lateinit var mVieMdoel: AboutUsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_about)

        init()
    }

    private fun init() {
        // initialise the view model
        mVieMdoel = ViewModelProviders.of(this).get(AboutUsViewModel::class.java)

        img_back.setOnClickListener {
            this@ScreenAbout.finish()
        }

        // Get data from teh web
        if (!isNetworkConnected) {
            showToast(resources.getString(R.string.provide_internet))
            return
        }

        mVieMdoel.liveResponse(sessionInstance.loggedInUserDetail.data!!.user!!.token).observe(this, Observer {
            when (it?.status) {
                true -> {
                    //     txt_about_us.text = getHtmlText(it.data!!.description)
                    //    loadImageWithGlideBitmap(img_admin, it.data.image_url)

                    webview_post_trip.settings?.apply {

                        javaScriptEnabled = true
                        allowContentAccess = true
                        domStorageEnabled = false
                        loadWithOverviewMode = true
                        useWideViewPort = true

                    }

                    webview_post_trip.loadData(it.data!!.description!!, "text/html", "UTF-8")
                    // onclick specific event handling
                    webview_post_trip.webViewClient = webViewClient

                }

            }


        })


    }


    val webViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, request: String): Boolean {
            try {

                return true
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
