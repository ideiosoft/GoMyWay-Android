package ssc.snow.app.gomyways.views.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class MyBaseAdapter : RecyclerView.Adapter<MyBaseAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    inner class MyViewHolder(// each data item is just a string in this case
            private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(obj: Any) {

         //   binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, getLayoutIdForType(viewType), parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getDataAtPosition(position))
    }


    abstract fun getDataAtPosition(position: Int): Any

    abstract fun getLayoutIdForType(viewType: Int): Int

}