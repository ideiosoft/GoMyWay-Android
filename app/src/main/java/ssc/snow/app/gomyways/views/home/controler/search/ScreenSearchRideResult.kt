package ssc.snow.app.gomyways.views.home.controler.search

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.screen_search_ride_result.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelSearchRideResult
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.views.home.controler.search.adapter.AdapterSearchedRideResult
import ssc.snow.app.gomyways.views.home.controler.search.viewmodel.SearchRideViewModel

class ScreenSearchRideResult : CommonActivity() {

    private var mLinearLayoutManager: LinearLayoutManager? = null
    private lateinit var mAdapter: AdapterSearchedRideResult
    private lateinit var mViewModel: SearchRideViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_search_ride_result)

        // initial implementation of views
        init()
        getresults()
    }

    private fun getresults() {
        mViewModel.liveResponseSearchRideResult().observe(this, Observer {
            if (it != null) {
                if (it.status!!) {
                    // set the data from to destination
                    Log.wtf("data", InjectorUtil.mToBeSearchRide)
                    txt_or_ds.text = "${InjectorUtil.mToBeSearchRide.split("|")[0]} -TO- ${InjectorUtil.mToBeSearchRide.split("|")[1]}"
                    txt_dt.text = "${InjectorUtil.mToBeSearchRide.split("|")[2]}"

                    setUpRecycleView(it.data)
                }
            }
        })

    }

    private fun init() {

        mViewModel = ViewModelProviders.of(this).get(SearchRideViewModel::class.java)
        // To prevent layout from going top
        recycler_search_result!!.isFocusable = false
        recycler_search_result!!.requestFocus()
        mLinearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_search_result!!.layoutManager = mLinearLayoutManager


        img_back.setOnClickListener {
            this@ScreenSearchRideResult.finish()
        }
        ll_post_request.setOnClickListener { this@ScreenSearchRideResult.finish() }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun setUpRecycleView(data: List<ModelSearchRideResult.Data?>?) {

        // Set adapter
        applicationContext!!.let {
            mAdapter = AdapterSearchedRideResult(this@ScreenSearchRideResult, data)
            recycler_search_result!!.adapter = mAdapter

        }


    }

}