package ssc.snow.app.gomyways.views.home.controler.post_request;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.utility.CommonActivity;

public class ScreenPostARequest extends CommonActivity {

    @InjectView(R.id.edt_src)
    EditText edtSrc;
    @InjectView(R.id.edt_destination)
    EditText edtDestination;
    @InjectView(R.id.edt_leaving)
    EditText edtLeaving;
    @InjectView(R.id.edt_seat_required)
    EditText edtSeatRequired;
    @InjectView(R.id.edt_description)
    EditText edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_post_a_request);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.img_back, R.id.edt_src, R.id.edt_destination, R.id.edt_leaving, R.id.txt_post_request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.edt_src:
                break;
            case R.id.edt_destination:
                break;
            case R.id.edt_leaving:
                break;
            case R.id.txt_post_request:
                goForNextScreenWithoutFinish(ScreenRequestPosted.class);
                break;
        }
    }
}
