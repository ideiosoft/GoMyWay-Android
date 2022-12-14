package ssc.snow.app.gomyways.views.home.controler.menu.help

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_screen_help.*
import kotlinx.android.synthetic.main.toolbar_child.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelHelp
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.HelpViewModel
import ssc.snow.app.gomyways.views.home.adapter.AdapterHelp

class  ScreenHelp : CommonActivity() {


    lateinit var mViewModel: HelpViewModel
    lateinit var mAdapterPayout: AdapterHelp
    private lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_help)

        // initialisations
        init()

    }

    private fun init() {

        // initialisation view model
        mViewModel = ViewModelProviders.of(this).get(HelpViewModel::class.java)

        // Set layout manager to the recycler view
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycle_help.layoutManager = layoutManager



        txt_center_heading.text = "Help"
        img_back.setOnClickListener {
            this@ScreenHelp.finish()
        }

        if (!isNetworkConnected) return

        mViewModel.liveResponse(sessionInstance.loggedInUserDetail.data!!.user!!.token).observe(this, Observer {

            if (it != null) {
                if (it.status) {
                    setupRecyclerView(it.data)
                }
            }

        })


    }

    private fun setupRecyclerView(mList: List<ModelHelp.Data?>?) {

        applicationContext?.let {

            mAdapterPayout = AdapterHelp(it, mList)
            recycle_help.adapter = mAdapterPayout

            // animate the items to be added to the recyelrview
            animationToItemsFadeIn(recycle_help)
        }


    }
}
