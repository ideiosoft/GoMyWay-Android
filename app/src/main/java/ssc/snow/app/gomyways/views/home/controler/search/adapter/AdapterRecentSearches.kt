package ssc.snow.app.gomyways.views.home.controler.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_recent_searches.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelRecentSearches
import ssc.snow.app.gomyways.data.utility.getHtmlText

class AdapterRecentSearches(private val dataList: List<ModelRecentSearches.Data?>?) : RecyclerView.Adapter<AdapterRecentSearches.MyViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_item_recent_searches, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) { //
        dataList!!.get(i)!!.run {
            holder.mSearchRides.text = getHtmlText("<font color='#ff4f02'>${origin}</font>  TO <font color='#0AAF92'>${destination}</font> ")
        }


    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mSearchRides = view.txt_trip_from_to

    }


}