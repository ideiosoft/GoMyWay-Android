package ssc.snow.app.gomyways.views.home.controler;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.utility.CommonActivity;

public class ScreenBookToRequest extends CommonActivity {

    @InjectView(R.id.txt_center_heading)
    TextView txtCenterHeading;
    @InjectView(R.id.txt_pickup)
    TextView txtPickup;
    @InjectView(R.id.txt_date)
    TextView txtDate;
    @InjectView(R.id.txt_time)
    TextView txtTime;
    @InjectView(R.id.txt_drop_off)
    TextView txtDropOff;
    @InjectView(R.id.txt_date_drop)
    TextView txtDateDrop;
    @InjectView(R.id.txt_time_drop)
    TextView txtTimeDrop;
    @InjectView(R.id.img_user)
    CircleImageView imgUser;
    @InjectView(R.id.txt_name_user)
    TextView txtNameUser;
    @InjectView(R.id.txt_description)
    TextView txtDescription;
    @InjectView(R.id.txt_source_to_destination)
    TextView txtSourceToDestination;
    @InjectView(R.id.txt_date_time_source_destination)
    TextView txtDateTimeSourceDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_book_to_request);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.img_back, R.id.txt_top_right_next, R.id.txt_next_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.txt_top_right_next:
                goForNextScreenWithoutFinish(ScreenBookToRequest2.class);
                break;
            case R.id.txt_next_bottom:
                goForNextScreenWithoutFinish(ScreenBookToRequest2.class);
                break;
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}

