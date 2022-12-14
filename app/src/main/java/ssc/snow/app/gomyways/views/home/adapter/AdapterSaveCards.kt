package ssc.snow.app.gomyways.views.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_row_save_cards.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelSavedBankCards
import ssc.snow.app.gomyways.views.home.controler.menu.bank_details.IBankCardsCallback


class AdapterSaveCards(private val context: Context,
                       private val mListData: List<ModelSavedBankCards.Data?>,
                       private val mCallback: IBankCardsCallback) : RecyclerView.Adapter<AdapterSaveCards.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_row_save_cards, parent, false))
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        mListData[position]?.run {
//
//            holder.mTxtName.text = card_name
//            holder.mTxtType.text = card_type
//            holder.mTxtNo.text = card_number
//           // holder.mTxtBvn.text = bvn
//            holder.mTxtExpire.text = "${expire_month + "/" + expire_year}"
//
//        }
//
//        holder.mImgCar.setOnClickListener {
//            mCallback.deleteCard(mListData[position]?.id.toString())
//
//        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val mImgCar = view.img_del
        val mTxtName = view.txt_name_value
        val mTxtType = view.txt_card_type_value
        val mTxtNo = view.txt_card_no_value
        val mTxtExpire = view.txt_expire_value
        val mTxtBvn = view.txt_bvn_value


    }
}
