package ssc.snow.app.gomyways.views.home.controler;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.utility.CommonActivity;

public class ScreenBookToRequest5 extends CommonActivity {

    @InjectView(R.id.edt_response)
    EditText edtResponse;
    @InjectView(R.id.txt_description)
    TextView txtDescription;
    @InjectView(R.id.txt_date_time_source_destination)
    TextView txtDateTimeSourceDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_book_to_request5);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.img_back, R.id.txt_top_right_next, R.id.txt_next_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.txt_top_right_next:
                goForNextScreenWithoutFinish(ScreenBookToRequest6.class);
                break;
            case R.id.txt_next_bottom:
                goForNextScreenWithoutFinish(ScreenBookToRequest6.class);
                break;
        }
    }
}
