package ssc.snow.app.gomyways.views.home.controler.menu.promotions

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_screen_rewards.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelRewards
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.PromotionViewModel
import ssc.snow.app.gomyways.views.home.adapter.AdapterRewards

class ScreenRewards : CommonActivity() {


    lateinit var mViewModel: PromotionViewModel
    lateinit var mAdapterRewards: AdapterRewards
    private lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_rewards)

        // initialisations
        init()

    }

    private fun init() {

        // initialisation view model
        mViewModel = ViewModelProviders.of(this).get(PromotionViewModel::class.java)

        // Set layout manager to the recycler view
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycle_rewards.layoutManager = layoutManager

        img_back.setOnClickListener {
            this@ScreenRewards.finish()
        }

        if (!isNetworkConnected) return

        mViewModel.liveResponseRewards(sessionInstance.loggedInUserDetail.data!!.user!!.token).observe(this, Observer {

            if (it != null) {
                if (it.status) {
                    // set total earning
                    txt_total_earning.text = "NGN ${it.data!!.total_earning.toString()}"
                    setupRecyclerView(it.data.users)

                }
            }

        })


    }

    private fun setupRecyclerView(mList: List<ModelRewards.Data.User?>?) {

        applicationContext?.let {

            mAdapterRewards = AdapterRewards(it, mList)
            recycle_rewards.adapter = mAdapterRewards

            // animate the items to be added to the recyelrview
            animationToItemsFadeIn(recycle_rewards)
        }


    }
}
