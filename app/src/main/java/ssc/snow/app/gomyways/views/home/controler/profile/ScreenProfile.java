package ssc.snow.app.gomyways.views.home.controler.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.model.ModelLogin;
import ssc.snow.app.gomyways.data.network.ApiEnum;
import ssc.snow.app.gomyways.data.utility.CommonActivity;
import ssc.snow.app.gomyways.views.home.adapter.AdapterUpcomingRequestsInProfile;

public class ScreenProfile extends CommonActivity {

    @InjectView(R.id.txt_center_heading)
    TextView txtCenterHeading;
    @InjectView(R.id.img_user)
    ImageView imgUser;
    @InjectView(R.id.txt_name)
    TextView txtName;
    @InjectView(R.id.txt_gender_age)
    TextView txtGenderAge;
    @InjectView(R.id.txt_about)
    TextView txtAbout;
    @InjectView(R.id.txt_joined)
    TextView txtJoined;
    @InjectView(R.id.txt_email_verify)
    TextView txtEmailVerify;
    @InjectView(R.id.txt_mobile_verify)
    TextView txtMobileVerify;
    @InjectView(R.id.txt_gov_id_verified)
    TextView txtGovIdVerified;

    @InjectView(R.id.ratingBar)
    RatingBar ratingBar_;
    @InjectView(R.id.txt_review_count)
    TextView txtReviewCount;

    @InjectView(R.id.recycle_upcoming_request)
    RecyclerView recycleUpcomingRequests;

    private AdapterUpcomingRequestsInProfile mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_profile);
        ButterKnife.inject(this);

        Log.wtf("enum_v", "" + ApiEnum.NORTH.ordinal());

        //        Log.wtf("enum_v0", "" + ApiEnum.TALKING);
        //        Log.wtf("enum_e", "" + ApiEnum.TALKING);

        setupRecyclerView(getSessionInstance().getLoggedInUserDetail().getData().getUpcoming_trips());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // update tjhe views with the values
        if (getSessionInstance().getLoggedInUserDetail().getData().getUser() != null) {
            updateUI(getSessionInstance().getLoggedInUserDetail().getData().getUser());
        }
    }

    private void setupRecyclerView(List<ModelLogin.Data.UpcomingTrip> mData) {
        // setup upcoming request in the list

        //   recycleUpcomingRequests.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        //   mAdapter = new AdapterUpcomingRequestsInProfile(this, mData);
        //   recycleUpcomingRequests.setAdapter(mAdapter);


    }

    private void updateUI(ModelLogin.Data.User user) {

        txtCenterHeading.setText(user.getFirst_name() + " " + user.getLast_name());
        txtName.setText(txtCenterHeading.getText().toString());
        txtAbout.setText(user.getAbout_user());
        txtJoined.setText(user.getJoined());

        ratingBar_.setRating(Float.parseFloat(String.valueOf(user.getAvgRating())));
        txtReviewCount.setText("Reviews: " + user.getNumber_of_reviews());

        // gov id
        if (user.getGoverment_issued_id().equalsIgnoreCase("Verified")) {
            txtGovIdVerified.setText(user.getGoverment_issued_id());
            txtGovIdVerified.setTextColor(getResources().getColor(R.color.green));
        } else {
            txtGovIdVerified.setText(user.getGoverment_issued_id());
            txtGovIdVerified.setTextColor(getResources().getColor(R.color.colorPrimary));
        }


        // gender
        if (user.getGender().equals("1"))
            txtGenderAge.setText("Male," + user.getAge());
        else if (user.getGender().equals("2"))
            txtGenderAge.setText("Female," + user.getAge());
        else
            txtGenderAge.setText("Other," + user.getAge());

        // email
        if (user.getEmail_status().equals("1")) {
            txtEmailVerify.setText("Verified");
            txtEmailVerify.setTextColor(getResources().getColor(R.color.green));
        } else {
            txtEmailVerify.setText("Unverified");
            txtEmailVerify.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        // phone
        if (user.getMobile_status().equals("1")) {
            txtMobileVerify.setText("Verified");
            txtMobileVerify.setTextColor(getResources().getColor(R.color.green));
        } else {
            txtMobileVerify.setText("Unverified");
            txtMobileVerify.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        // load user user profile image
       //imgUser.setImageResource(0);
        loadImageWithGlideBitmap(imgUser, user.getProfile_image());


    }

    @OnClick({R.id.img_back, R.id.img_edit, R.id.ll_email_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;

            case R.id.img_edit:
                goForNextScreenWithoutFinish(ScreenEditProfile.class);
                break;
        }
    }

}
