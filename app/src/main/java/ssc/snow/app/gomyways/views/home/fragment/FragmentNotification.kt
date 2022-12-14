package ssc.snow.app.gomyways.views.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_fragment_notification.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelAllNotifications
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.NotificationsViewModel
import ssc.snow.app.gomyways.views.home.adapter.AdapterNotifications

class FragmentNotification : Fragment() {


    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mAdapter: AdapterNotifications? = null

    private lateinit var mViewModel: NotificationsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_fragment_notification, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialisations
        init()

    }


    private fun init() {
        // Initialisations of view model
        mViewModel = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        // To prevent layout from going top
        recycle_notificaions.apply {
            isFocusable = false
            requestFocus()
            layoutManager = mLinearLayoutManager
        }

        if (InjectorUtil.isNetworkConnected()) {
            mViewModel.getNotifications()
        }
        mViewModel.liveResponse().observe(viewLifecycleOwner, Observer {

            if (it != null) {
                if (it.status!!) {
                    // setup recycler view
                    setupRecyclerView(it.data)
                }
            }

        })



    }

    fun setupRecyclerView(data: List<ModelAllNotifications.Data?>?) {

        recycle_notificaions!!.apply {

            // set adapter
            mAdapter = AdapterNotifications(data)
            adapter = mAdapter
        }
    }


}