package ssc.snow.app.gomyways.views.home.controler.menu.promotions

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_screen_offers.*
import kotlinx.android.synthetic.main.toolbar_child.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelOffers
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.PromotionViewModel
import ssc.snow.app.gomyways.views.home.adapter.AdapterOffers

class ScreenOffers : CommonActivity() {


    lateinit var mViewModel: PromotionViewModel
    lateinit var mAdapterOffers: AdapterOffers
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_offers)

        // initialisations
        init()

    }

    private fun init() {

        // initialisation view model
        mViewModel = ViewModelProviders.of(this).get(PromotionViewModel::class.java)

        // Set layout manager to the recycler view
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycle_offers.layoutManager = layoutManager

        txt_center_heading.text = "Offers"
        img_back.setOnClickListener {
            this@ScreenOffers.finish()
        }

        if (!isNetworkConnected) return

        mViewModel.liveResponseOffers(sessionInstance.loggedInUserDetail.data!!.user!!.token).observe(this, Observer {

            if (it != null) {
                if (it.status) {
                    setupRecyclerView(it.data)
                }
            }

        })


    }

    private fun setupRecyclerView(mList: List<ModelOffers.Data?>?) {

        applicationContext?.let {

            mAdapterOffers = AdapterOffers(it, mList)
            recycle_offers.adapter = mAdapterOffers

            // animate the items to be added to the recyelrview
            animationToItemsFadeIn(recycle_offers)
        }


    }
}
