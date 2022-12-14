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
import kotlinx.android.synthetic.main.fragment_message.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.inbox.ModelInboxList
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.inbox.InboxListViewModel
import ssc.snow.app.gomyways.views.home.adapter.AdapterInbox

class FragmentMessage : Fragment() {

    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mAdapterInbox: AdapterInbox? = null

    lateinit var mViewModel: InboxListViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_message, container, false)

        mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mViewModel = ViewModelProviders.of(this).get(InboxListViewModel::class.java)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialisations
        init()
    }

    private fun init() {

        recycle_inbox.apply {
            isFocusable = false
            requestFocus()
            layoutManager = mLinearLayoutManager
        }

        if (InjectorUtil.isNetworkConnected()) {
            mViewModel.getInboxList()
        }
        // get the live data observer
        mViewModel.liveResponse().observe(viewLifecycleOwner, Observer {

            if (it != null) {
                if (it.status!!) {
                    // setup recycler view
                    initRecyclerView(it.data)
                }
            }
        })
    }

    private fun initRecyclerView(data: List<ModelInboxList.Data?>?) {
        // To prevent layout from going top


        data?.let { _ ->
            recycle_inbox.apply {
                // set adapter
                mAdapterInbox = data.let { activity?.let { it1 -> AdapterInbox(it1, it) } }
                adapter = mAdapterInbox
            }
        }
    }
}