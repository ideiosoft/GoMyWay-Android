package ssc.snow.app.gomyways.views.home.controler.menu.bank_details

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_screen_bank_details.*
import kotlinx.android.synthetic.main.toolbar_child.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.BankDetailsViewModel

class ScreenBankDetails : CommonActivity() {

    lateinit var mViewModel: BankDetailsViewModel
    //  lateinit var mAdapterSaveCards: AdapterSaveCards

    //  private lateinit var layoutManager: RecyclerView.LayoutManager
    val mBankNamesList = arrayListOf<String>()

    private var mFlagDel: Boolean = false

    // private var mMonthId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_bank_details)

        // Get data for initial use
        initialDataLoading()

        // Set on event listeners
        onClicks()

        // For initial live data receiver
        init()

    }

    private fun initialDataLoading() {

        // Initialisation of view model
        mViewModel = ViewModelProviders.of(this).get(BankDetailsViewModel::class.java)

        mViewModel.mLiveBankNames.observe(this, Observer { mObserevData ->

            mObserevData?.let {

                it.data?.forEach { mList ->
                    mBankNamesList.add(mList?.name!!)
                }


                // Initialize a new array adapter object for card types
                val adapterCardTypes = ArrayAdapter<String>(this, R.layout.layout_row_drop_single_item, mBankNamesList)


                // Set the AutoCompleteTextView adapter
                edt_bank_name.setAdapter(adapterCardTypes)
                // Auto complete threshold
                // The minimum number of characters to type to show the drop down
                edt_bank_name.threshold = 1

                edt_bank_name.setOnClickListener {
                    edt_bank_name.showDropDown()
                }

                // Set an item click listener for auto complete text view
                edt_bank_name.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val selectedItem = parent.getItemAtPosition(position).toString()

                    edt_bank_name.setText(selectedItem)
                }
            }


        })
//        mViewModel.mLiveMonths.observe(this, Observer {
//            // Initialize a new array adapter object for card months
//            val adapterMonths = ArrayAdapter<String>(this, R.layout.layout_row_drop_single_item, it)
//
//            // Set the AutoCompleteTextView adapter
//            edt_exp_month.setAdapter(adapterMonths)
//            // Auto complete threshold
//            // The minimum number of characters to type to show the drop down
//            edt_exp_month.threshold = 1
//
//            edt_exp_month.setOnClickListener {
//                edt_exp_month.showDropDown()
//            }
//
//            // Set an item click listener for auto complete text view
//            edt_exp_month.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
//                val selectedItem = parent.getItemAtPosition(position).toString()
//
//                edt_exp_month.setText(selectedItem)
//
//            }
//
//        })
//        mViewModel.mLiveYears.observe(this, Observer {
//
//            // Initialize a new array adapter object for card Years
//            val adapterYears = ArrayAdapter<String>(this, R.layout.layout_row_drop_single_item, it)
//
//            // Set the AutoCompleteTextView adapter
//            edt_exp_year.setAdapter(adapterYears)
//
//            // Auto complete threshold
//            // The minimum number of characters to type to show the drop down
//            edt_exp_year.threshold = 1
//
//            // set on click listeneres
//            edt_exp_year.setOnClickListener {
//                edt_exp_year.showDropDown()
//            }
//
//            // Set an item click listener for auto complete text view
//            edt_exp_year.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
//                val selectedItem = parent.getItemAtPosition(position).toString()
//
//                edt_exp_year.setText(selectedItem)
//            }
//
//        })
//
//


    }

    private fun onClicks() {

        img_back.setOnClickListener {
            this@ScreenBankDetails.finish()
        }

        btn_add_bd.setOnClickListener {

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return@setOnClickListener
            }

            when {
                edt_ban.text.isNullOrEmpty() -> edt_ban.error = resources.getString(R.string.field_cnt_empty)
                edt_yn.text.isNullOrEmpty() -> edt_yn.error = resources.getString(R.string.field_cnt_empty)
                edt_bank_name.text.isNullOrEmpty() -> edt_bank_name.error = resources.getString(R.string.field_cnt_empty)
                else -> {
                    //              mMonthId = getMonthId(edt_exp_month.text.toString())

                    hideSoftKeyboard()
                    mFlagDel = true
                    mViewModel.addBankAccountNumber(
                            sessionInstance.loggedInUserDetail.data!!.user?.token.toString(),
                            edt_ban.text.toString(), edt_yn.text.toString(),
                            edt_bank_name.text.toString())
                }
            }


            //. class make it true for the add dialog shownok


        }
    }

    private fun init() {
        txt_center_heading.text = "Bank Details"

        // Set layout manager to the recycler view
//        layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//        recycle_save_cards.layoutManager = layoutManager
//
        if (isNetworkConnected) {
            mViewModel.getBankDetail(sessionInstance.loggedInUserDetail.data!!.user!!.token)
        }

        mViewModel.liveCardResponse().observe(this, Observer { mModel ->
            mModel?.let {
                if (mModel.status!!) {
                    // Set the values to the views

                    edt_ban.setText("${mModel.data?.account_number}")
                    edt_yn.setText("${mModel.data?.bank_user_name}")
                    edt_bank_name.setText("${mModel.data?.bank_name}")

                    if (mFlagDel) {

                        mFlagDel = false
                        successBox(mModel.message.toString())
                    }
//
//                    mModel.data?.let {
//                        setupRecyclerView(it)
//
//                    }

                    // Set recycler view
                } else {
                    showToast(getHtmlText(mModel.message))
                }
            }
        })

    }

    val mCallback = object : IBankCardsCallback {

        override fun deleteCard(mId: String) {

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return

            }
            // confirm dialog
            //  confirmDelete("Do you want to delete?", mId)
        }
    }
//

//    fun confirmDelete(msg: String?, mId: String) {
//        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Confirmation")
//                .setContentText(Html.fromHtml(msg).toString())
//                .setConfirmButton("Confirm") { sDialog ->
//                    sDialog.dismissWithAnimation()
//
//                    if (!isNetworkConnected) {
//                        showToast(resources.getString(R.string.provide_internet))
//
//                    } else {
//
//                        // Show progress when firing api
//                        mFlagDel = true
//                        mViewModel.delBankCard(sessionInstance.loggedInUserDetail.data!!.user!!.token, mId)
//
//
//                    }
//                }
//                .setCancelButton("Cancel") { sDialog ->
//                    sDialog.dismissWithAnimation()
//                }
//                .show()
//    }

//    private fun setupRecyclerView(mList: List<ModelSavedBankCards.Data?>) {
//
//        applicationContext?.let {
//
//            mAdapterSaveCards = AdapterSaveCards(it, mList, mCallback)
//            recycle_save_cards.adapter = mAdapterSaveCards
//
//            // animate the items to be added to the recyclerView
//            animationToItemsFadeIn(recycle_save_cards)
//        }
//
//    }

    // Show the success box to confirm user that data is updated
    private fun successBox(msg: String) {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText(msg)
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                }
                .show()
    }

}
