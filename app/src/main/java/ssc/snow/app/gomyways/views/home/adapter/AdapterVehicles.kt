package ssc.snow.app.gomyways.views.home.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_row_vehicles.view.*
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelUserVehicles
import ssc.snow.app.gomyways.views.home.controler.profile.IProfileCallback
import java.util.*


class AdapterVehicles(
        private val context: Context,
        private val mListData: List<ModelUserVehicles.Data.UserVehicle?>?,
        private val mCallback: IProfileCallback) : RecyclerView.Adapter<AdapterVehicles.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_row_vehicles, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mListData!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mListData!!.get(position)?.run {

            holder.mTxtCarName.text = vehicle_name
            holder.mTxtModel.text = model
            holder.mTxtVehicleType.text = vehicle_type
            holder.mTxtPlateNo.text = plate_number

            // load image from network in case first laoding and secod time from cache
            holder.updateWithUrl(vehicle_image_url)
            // set view pager
            if (!vehicle_image_url.isNullOrEmpty()) {
                holder.setViewPager(vehicle_image_url)
            }


        }

        holder.mImgDel.setOnClickListener {
            mCallback.delUserVehicle(mListData.get(position)?.id.toString())

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mImgCar = view.img_center_car
        val mImgDel = view.img_del
        val mTxtCarName = view.txt_name_car
        val mTxtModel = view.txt_model_value
        val mTxtVehicleType = view.txt_vehicle_type_value
        val mTxtPlateNo = view.txt_add_vehicle

        val mViewPager2 = view.view_pager2
        val mIndicator = view.indicator


        var currentPage = 0
        var timer: Timer? = null
        val DELAY_MS: Long = 1000 //delay in milliseconds before task is to be executed

        val PERIOD_MS: Long = 3500 // time in milliseconds between successive task executions.


        fun updateWithUrl(url: String?) {
            // load image from network in case first laoding and secod time from cache
            Glide.with(itemView.context)
                    .load(url)
                    .into(mImgCar)
        }

        fun setViewPager(vehicleImageUrl: String) {
            var mList = vehicleImageUrl.split(",")

            // set adapter for the images
            val mAdapterViewPager = ViewPagerAdapterVehicle(mList)

            mViewPager2.adapter = mAdapterViewPager
            mViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            // Set indicator on view pager
            mIndicator.setViewPager(mViewPager2)

            // Register adapter  optional
            mAdapterViewPager.registerAdapterDataObserver(mIndicator.adapterDataObserver)

            // set the time to auto slide
            /*After setting the adapter use the timer */
            /*After setting the adapter use the timer */
            val handler = Handler()
            val Update = Runnable {
                if (currentPage == mList.size - 1) {
                    currentPage = 0
                }
                mViewPager2.setCurrentItem(currentPage++, true)
            }

            timer = Timer() // This will create a new Thread
            timer!!.schedule(object : TimerTask() {
                // task to be scheduled
                override fun run() {
                    handler.post(Update)
                }
            }, DELAY_MS, PERIOD_MS)


        }


    }
}
