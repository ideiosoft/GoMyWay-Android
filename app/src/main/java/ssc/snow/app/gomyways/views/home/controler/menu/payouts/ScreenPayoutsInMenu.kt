package ssc.snow.app.gomyways.views.home.controler.menu.payouts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_screen_payments_in_menu.*
import kotlinx.android.synthetic.main.toolbar_child.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelPayouts
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.PayoutViewModel
import ssc.snow.app.gomyways.views.home.adapter.AdapterPayouts

class ScreenPayoutsInMenu : CommonActivity() {


    lateinit var mViewModel: PayoutViewModel
    lateinit var mAdapterPayout: AdapterPayouts
    private lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_payments_in_menu)
        ButterKnife.inject(this)

        // initialisations
        init()


    }

    private fun setupRecyclerView(mList: List<ModelPayouts.Data?>) {


        applicationContext?.let {

            mAdapterPayout = AdapterPayouts(it, mList)
            recycle_payout.adapter = mAdapterPayout

            // animate the items to be added to the recyelrview
            animationToItemsFadeIn(recycle_payout)
        }


    }

    private fun init() {
        txt_center_heading!!.text = resources.getString(R.string.payouts)

        // Set layout manager to the recycler view
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycle_payout.layoutManager = layoutManager

        // Initialisation of view model
        mViewModel = ViewModelProviders.of(this).get(PayoutViewModel::class.java)


        if (isNetworkConnected) {
            mViewModel.getAllPayouts(sessionInstance.loggedInUserDetail.data!!.user!!.token)
        }

        mViewModel.liveResponse().observe(this, Observer { mModel ->


            if (mModel != null) {
                if (mModel.status) {
                    // set the values to the views
                    mModel.data?.let {

                        if (it.size == 0) {
                            txt_no_data.visibility = View.VISIBLE
                            recycle_payout.visibility = View.GONE

                            txt_no_data.text = "No payout yet!"

                        } else
                            setupRecyclerView(it)
                    }

                    // set recycler view
                } else {
                    showToast(mModel.message)
                }
            }
        })


        // click, listeners
        img_back.setOnClickListener {
            this@ScreenPayoutsInMenu.finish()

        }


    }


}
