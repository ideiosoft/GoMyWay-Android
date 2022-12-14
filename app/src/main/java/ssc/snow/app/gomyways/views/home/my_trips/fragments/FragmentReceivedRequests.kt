package ssc.snow.app.gomyways.views.home.my_trips.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_screen_post_atrip.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.my_trips.MyTripsViewModel


class FragmentReceivedRequests : Fragment() {


    //  private var mLinearLayoutManager: LinearLayoutManager? = null
    //  private lateinit var mAdapter: AdapterReceiveRequests
    private lateinit var mViewModel: MyTripsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_request_received, container, false)

        // Calling initial trips
        mViewModel = ViewModelProviders.of(this).get(MyTripsViewModel::class.java)
        return v
    }

    override fun onResume() {
        super.onResume()
        // initiate webview
        initWebView()
    }

    private fun initWebView() {

        webview_post_trip.settings?.apply {

            javaScriptEnabled = true
            allowContentAccess = true
            domStorageEnabled = false

            loadWithOverviewMode = true

            useWideViewPort = true

        }



        if (InjectorUtil.isNetworkConnected()) {
            // Observe data
            mViewModel.liveRequestReceived().observe(viewLifecycleOwner, Observer {
                Log.wtf("mUrl", it)

                // load web url
                webview_post_trip.loadUrl(it)
                // onclick specific event handling
                webview_post_trip.webViewClient = webViewClient
            })



        } else
            InjectorUtil.showToast(resources.getString(R.string.provide_internet))


    }

    val webViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.wtf("mUrl_s", "" + url)

//            //Todo: Pending
//            if (url.equals(InjectorUtil.TRIP_SUBMIT_SUCCESSFULLY + InjectorUtil.sessionGoMyWay().loggedInUserDetail.data!!.user!!.token, true)) {
//                goForHomeFromLeftToRight(ScreenHome::class.java)
//            }

        }

        override fun shouldOverrideUrlLoading(view: WebView, request: String): Boolean {
            try {
                Log.wtf("mUrl_l", "" + request)

                view.loadUrl(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }
    }


//
//    private fun init() {
//
//        // To prevent layout from going top
//        recycle_upcoming!!.isFocusable = false
//        recycle_upcoming!!.requestFocus()
//        mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//        recycle_upcoming!!.layoutManager = mLinearLayoutManager
//
//
//
//    fun setUpRecycleView(data: List<ModelRequestReceived.Data?>?) {
//
//        // Set adapter
//        activity!!.let {
//            mAdapter = AdapterReceiveRequests(activity!!, data)
//            recycle_upcoming!!.adapter = mAdapter
//
//        }
//
//
//    }

}
