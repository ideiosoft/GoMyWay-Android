package ssc.snow.app.gomyways.views.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_row_emails_.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelGetAllEmails
import ssc.snow.app.gomyways.views.home.controler.profile.IEmailCallback


class AdapterEmails(
        private val context: Context,
        private val mListData: List<ModelGetAllEmails.Data.OtherEmail?>?,
        private val mCallback: IEmailCallback) : RecyclerView.Adapter<AdapterEmails.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.layout_row_emails_, parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return mListData!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mListData!!.get(position)?.run {

            holder.mTxtEmail.text = "● ${email}"

            if (status.equals("0")) {
                holder.mTxtVerify.text = "(Unverified)"

                holder.mBtnMakePrimary.visibility = View.GONE
                holder.mTxtVerify.setTextColor(context.resources.getColor(R.color.color_orange_gradient_1))

            } else {
                holder.mTxtVerify.text = "(Verified)"

                holder.mBtnMakePrimary.visibility = View.VISIBLE
                holder.mTxtVerify.setTextColor(context.resources.getColor(R.color.green))
            }
        }

        // implement listenerers
        holder.mBtnMakePrimary.setOnClickListener {
            mCallback.makePrimaryEmail(mListData.get(position)?.email.toString(), "")

        }
        holder.mBtnReSendVerification.setOnClickListener {
            mCallback.resendVerificationEmail(mListData.get(position)?.email.toString())

        }
        holder.mBtnDelete.setOnClickListener {
            mCallback.delUserEmail(mListData.get(position)?.email.toString())

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val mTxtEmail = view.txt_email
        val mTxtVerify = view.txt_verify
        val mBtnMakePrimary = view.btn_make_primary
        val mBtnReSendVerification = view.btn_re_send
        val mBtnDelete = view.btn_del


    }
}
