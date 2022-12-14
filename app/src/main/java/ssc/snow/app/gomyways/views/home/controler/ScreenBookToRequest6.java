package ssc.snow.app.gomyways.views.home.controler;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.views.home.ScreenHome;
import ssc.snow.app.gomyways.data.utility.CommonActivity;

public class ScreenBookToRequest6 extends CommonActivity {

    @InjectView(R.id.txt_authorised_amout)
    TextView txtAuthorisedAmout;
    @InjectView(R.id.txt_enter_new_card)
    TextView txtEnterNewCard;
    @InjectView(R.id.txt_request_book_description)
    TextView txtRequestBookDescription;
    @InjectView(R.id.txt_cancellation_policy_desc)
    TextView txtCancellationPolicyDesc;
    @InjectView(R.id.txt_source_to_destination)
    TextView txtSourceToDestination;
    @InjectView(R.id.txt_date_time_source_destination)
    TextView txtDateTimeSourceDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_book_to_request6);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.img_back, R.id.txt_top_right_submit, R.id.txt_enter_new_card, R.id.txt_request_a_book, R.id.txt_next_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.txt_top_right_submit:
                goForHomeFromRightToLeft(ScreenHome.class);
                break;
            case R.id.txt_enter_new_card:
                break;
            case R.id.txt_request_a_book:
                break;
            case R.id.txt_next_bottom:
                goForHomeFromRightToLeft(ScreenHome.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
