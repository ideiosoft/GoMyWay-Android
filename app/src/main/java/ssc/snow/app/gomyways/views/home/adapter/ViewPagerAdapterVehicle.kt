package ssc.snow.app.gomyways.views.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_pager.view.*
import ssc.snow.app.gomyways.R


class ViewPagerAdapterVehicle(val mListImages: List<String>) : RecyclerView.Adapter<ViewPagerAdapterVehicle.PagerVH>() {

    //array of colors to change the background color of screen

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
            PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.row_view_pager, parent, false))

    //  get the size of color array
    //  override fun getItemCount(): Int = Int.MAX_VALUE
    override fun getItemCount(): Int = mListImages.size

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {

        holder.updateWithUrl(mListImages.get(position))


    }

    class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView = itemView.img_left_

        fun updateWithUrl(url: String?) {
            // load image from network in case first laoding and secod time from cache
            Glide.with(itemView.context)
                    .load(url)
                    .into(mImageView)
        }
    }
}

