package ssc.snow.app.gomyways.views.home.controler.post_request;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.utility.CommonActivity;

public class ScreenRequestSetting extends CommonActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_request_setting);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.img_back, R.id.txt_edit_request, R.id.txt_share_request, R.id.txt_post_new_request, R.id.txt_delete_request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.txt_edit_request:
                break;
            case R.id.txt_share_request:
                break;
            case R.id.txt_post_new_request:
                break;
            case R.id.txt_delete_request:
                break;
        }
    }
}
