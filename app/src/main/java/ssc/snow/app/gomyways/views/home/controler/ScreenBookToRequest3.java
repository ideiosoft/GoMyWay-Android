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

public class ScreenBookToRequest3 extends CommonActivity {

    @InjectView(R.id.txt_seats_count)
    TextView txtSeatsCount;
    @InjectView(R.id.txt_per_seat_price)
    TextView txtPerSeatPrice;
    @InjectView(R.id.txt_booking_fee_value)
    TextView txtBookingFeeValue;
    @InjectView(R.id.txt_total_value)
    TextView txtTotalValue;
    @InjectView(R.id.edt_promo_code)
    EditText edtPromoCode;
    @InjectView(R.id.txt_source_to_destination)
    TextView txtSourceToDestination;
    @InjectView(R.id.txt_date_time_source_destination)
    TextView txtDateTimeSourceDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_book_to_request3);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.img_back, R.id.txt_top_right_next, R.id.img_minus, R.id.img_plus,
            R.id.btn_apply_promo_code, R.id.txt_next_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.txt_top_right_next:
                goForNextScreenWithoutFinish(ScreenBookToRequest4.class);
                break;
            case R.id.img_minus:
                break;
            case R.id.img_plus:
                break;
            case R.id.btn_apply_promo_code:
                break;
            case R.id.txt_next_bottom:
                goForNextScreenWithoutFinish(ScreenBookToRequest4.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
