package ssc.snow.app.gomyways.views.home.my_trips.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_upcoming.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelMyTrips
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.my_trips.MyTripsViewModel
import ssc.snow.app.gomyways.views.home.my_trips.adapter.AdapterUpcomingTrips


class FragmentUpcoming : Fragment() {


    private var mLinearLayoutManager: LinearLayoutManager? = null
    private lateinit var mAdapter: AdapterUpcomingTrips

    private lateinit var mViewModel: MyTripsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_upcoming, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // calling initial trips
        mViewModel = ViewModelProviders.of(this).get(MyTripsViewModel::class.java)
        init()

    }

    private fun init() {

        // To prevent layout from going top
        recycle_upcoming!!.isFocusable = false
        recycle_upcoming!!.requestFocus()
        mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recycle_upcoming!!.layoutManager = mLinearLayoutManager


        if (InjectorUtil.isNetworkConnected()) {
            mViewModel.getUpcomingTrips()

        } else
            InjectorUtil.showToast(resources.getString(R.string.provide_internet))


        // Observe data
        mViewModel.liveUpcoming().observe(viewLifecycleOwner, Observer {
            Log.e("upcoming res",Gson().toJson(it))
            when (it?.status) {
                true -> {

                    if(it.data?.size!!>0)
                    {
                    setUpRecycleView(it.data)
                }}

            }

        })

    }


    fun setUpRecycleView(data: List<ModelMyTrips.Data?>?) {

        // Set adapter
        activity!!.let {
            mAdapter = AdapterUpcomingTrips(activity!!, data)
            recycle_upcoming!!.adapter = mAdapter

        }
    }

    override fun onResume() {
        super.onResume()
        init()

    }

   private var isViewShown = false

   override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (view != null && isVisibleToUser) {
            isViewShown = true
            init()
        } else {
            isViewShown = false
        }
    }

//    var _areLecturesLoaded = false
//
//    override fun setMenuVisibility(isVisibleToUser: Boolean) {
//        super.setMenuVisibility(isVisibleToUser)
//            if (isVisibleToUser && !_areLecturesLoaded) {
//                init() //Load your data here
//                _areLecturesLoaded = true
//            }
//    }
}