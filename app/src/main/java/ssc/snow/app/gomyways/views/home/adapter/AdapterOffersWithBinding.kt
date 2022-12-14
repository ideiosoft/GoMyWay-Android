package ssc.snow.app.gomyways.views.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_row_offers.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelOffers


class AdapterOffersWithBinding(
        private val context: Context,
        private val mListData: List<ModelOffers.Data?>?) : RecyclerView.Adapter<AdapterOffersWithBinding.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.layout_row_offers, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mListData!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mListData!!.get(position)!!.run {

            //  //  holder.mQuestion.text = "● ${title}"

            holder.mTxtOfferDate.text = "Latest offer (valid from: ${valid_from} to ${valid_to})"
            holder.mTxtCoupanName.text = "${coupon_name}"
            holder.mTxtCoupanCode.text = "${coupon_code}"


            holder.mImgCopy.setOnClickListener {
                // click to copy the coupan code
                clickToCopy(coupon_code)
            }


        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTxtCoupanName = view.txt_coupan_name
        val mTxtCoupanCode = view.txt_coupan_code
        val mTxtOfferDate = view.txt_offer_1
        val mImgCopy = view.txt_img_copy


    }

    fun clickToCopy(text: String?) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager?
            clipboard!!.text = text
        } else {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager?
            val clip = android.content.ClipData.newPlainText("Copied Text", text)
            clipboard!!.setPrimaryClip(clip)
        }

        // Show display after successfully
        Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
    }


}
