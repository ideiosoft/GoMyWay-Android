package ssc.snow.app.gomyways.views.home.my_trips.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_recent.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelMyTrips
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.my_trips.MyTripsViewModel
import ssc.snow.app.gomyways.views.home.my_trips.adapter.AdapterRecentTrips

class FragmentRecent : Fragment() {

    private var mLinearLayoutManager: LinearLayoutManager? = null
    private lateinit var mAdapter: AdapterRecentTrips

    private lateinit var mViewModel: MyTripsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_recent, container, false)
        // calling initial trips
        mViewModel = ViewModelProviders.of(this).get(MyTripsViewModel::class.java)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {

        // To prevent layout from going top
        recycle_recent!!.isFocusable = false
        recycle_recent!!.requestFocus()
        mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recycle_recent!!.layoutManager = mLinearLayoutManager


        if (InjectorUtil.isNetworkConnected()) {
            mViewModel.getRecentTrips()

        } else
            InjectorUtil.showToast(resources.getString(R.string.provide_internet))


        // observe data
        mViewModel.liveRecent().observe(viewLifecycleOwner, Observer {

            when (it?.status) {
                true -> {
                    if(it.data?.size!!>0)
                    {
                    setUpRecycleView(it.data)
                }
            }

            }
        })

    }


    fun setUpRecycleView(data: List<ModelMyTrips.Data?>?) {

        // Set adapter
        activity!!.let {
            mAdapter = AdapterRecentTrips(activity!!, data)
            recycle_recent!!.adapter = mAdapter

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
//        if (isVisibleToUser && !_areLecturesLoaded) {
//            init() //Load your data here
//            _areLecturesLoaded = true
//        }
//    }
}// Required empty public constructor
