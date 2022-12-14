package ssc.snow.app.gomyways.views.home.controler.post_request;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.utility.CommonActivity;

public class ScreenRequestPosted extends CommonActivity {


    @InjectView(R.id.txt_pickup_city)
    TextView txtPickupCity;
    @InjectView(R.id.txt_pickup_address)
    TextView txtPickupAddress;
    @InjectView(R.id.txt_drop_city)
    TextView txtDropCity;
    @InjectView(R.id.txt_drop_address)
    TextView txtDropAddress;
    @InjectView(R.id.txt_date_time)
    TextView txtDateTime;
    @InjectView(R.id.txt_seats_required)
    TextView txtSeatsRequired;
    @InjectView(R.id.txt_description)
    TextView txtDescription;
    @InjectView(R.id.img_user)
    CircleImageView imgUser;
    @InjectView(R.id.txt_name_user)
    TextView txtNameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_request_posted);
        ButterKnife.inject(this);


    }


    @OnClick({R.id.img_back, R.id.img_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.img_setting:
                goForNextScreenWithoutFinish(ScreenRequestSetting.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
