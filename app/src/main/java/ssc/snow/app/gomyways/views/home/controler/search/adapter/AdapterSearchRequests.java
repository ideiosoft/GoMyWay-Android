package ssc.snow.app.gomyways.views.home.controler.search.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.UiHandleMethods;

public class AdapterSearchRequests extends RecyclerView.Adapter<AdapterSearchRequests.MyViewHolder> {

    //   List<ModelTeams.DataEntity> mListData;
    private Activity context;
    private UiHandleMethods uihandle;

    public AdapterSearchRequests(Activity context) {
        this.context = context;
        //  this.mListData = mListData;
        uihandle = new UiHandleMethods(context);
    }

    @NonNull
    @Override
    public AdapterSearchRequests.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_search_trips, viewGroup, false);

        return new AdapterSearchRequests.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearchRequests.MyViewHolder holder, int i) {

//

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // send the control to the next screen for details
                //context.startActivity(new Intent(context, ScreenInboxDetail.class));
            }
        });


    }


    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);


        }
    }
}

