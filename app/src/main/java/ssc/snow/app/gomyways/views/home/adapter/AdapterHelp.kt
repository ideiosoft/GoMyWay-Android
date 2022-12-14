package ssc.snow.app.gomyways.views.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_row_help.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelHelp
import ssc.snow.app.gomyways.data.utility.html2text


class AdapterHelp(
        private val context: Context,
        private val mListData: List<ModelHelp.Data?>?) : RecyclerView.Adapter<AdapterHelp.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.layout_row_help, parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return mListData!!.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mListData!!.get(position)!!.run {

            holder.mQuestion.text = "● ${title}"
            holder.mAns.text = html2text(description)


        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mQuestion = view.txt_question
        val mAns = view.txt_answer


    }

    fun getHtmlText(mData: String?): String {

        return if (mData != null) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                Html.fromHtml(mData).toString()
            } else {
                Html.fromHtml(mData, Html.FROM_HTML_MODE_LEGACY).toString()
            }
        } else
            ""


    }


}
