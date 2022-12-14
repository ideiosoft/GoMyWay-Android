package ssc.snow.app.gomyways.views.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_row_payouts.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelPayouts


class AdapterPayouts(
        private val context: Context,
        private var mListData: List<ModelPayouts.Data?>) : RecyclerView.Adapter<AdapterPayouts.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.layout_row_payouts, parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mListData.get(position)!!.run {

            holder.mDriverName.text = "${first_name + " " + last_name}"
            holder.mTxnId.text = "Txn: ${transaction_id}"
            holder.mPrice.text = "NGN ${total_amount}"


            holder.mFrom.text = " ${origin}"
            holder.mTo.text = " ${destination}"

            holder.mFromdate.text = " ${leaving}"
            holder.mToDate.text = " ${total_amount}"


        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mDriverName = view.txt_name_driver
        val mTxnId = view.txt_transaction_id
        val mPrice = view.txt_price
        val mFrom = view.txt_origion
        val mFromdate = view.txt_date_from
        val mTo = view.txt_destination
        val mToDate = view.txt_date_to


    }
}
