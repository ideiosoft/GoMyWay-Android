package ssc.snow.app.gomyways.views.home.controler.post_request;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.utility.CommonActivity;
import ssc.snow.app.gomyways.views.home.controler.search.SearchRide;

public class ScreenPostOrRequestTrip extends CommonActivity {

    @InjectView(R.id.txt_description_post_trip)
    TextView txtDescriptionPostTrip;
    @InjectView(R.id.txt_description_request_trip)
    TextView txtDescriptionRequestTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_or_request_trip);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.card_post_request, R.id.card_post_trip, R.id.img_back, R.id.btn_continue_post_trip, R.id.btn_continue_post_a_request_})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;

            case R.id.btn_continue_post_trip:
            case R.id.card_post_trip:
                goForNextScreenWithoutFinish(ScreenPostATrip.class);
                break;

            case R.id.btn_continue_post_a_request_:
            case R.id.card_post_request:
                goForNextScreenWithoutFinish(SearchRide.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }




}

