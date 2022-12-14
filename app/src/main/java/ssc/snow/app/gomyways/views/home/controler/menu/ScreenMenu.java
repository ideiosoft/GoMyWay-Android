package ssc.snow.app.gomyways.views.home.controler.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.model.ModelCommon;
import ssc.snow.app.gomyways.data.model.ModelReferCode;
import ssc.snow.app.gomyways.data.utility.CommonActivity;
import ssc.snow.app.gomyways.viewmodel.ReferCodeViewModel;
import ssc.snow.app.gomyways.views.home.controler.menu.about.ScreenAbout;
import ssc.snow.app.gomyways.views.home.controler.menu.bank_details.ScreenBankDetails;
import ssc.snow.app.gomyways.views.home.controler.menu.email_verifications.ScreenEmailAddress;
import ssc.snow.app.gomyways.views.home.controler.menu.help.ScreenHelp;
import ssc.snow.app.gomyways.views.home.controler.menu.idverification.ScreenIdVerification;
import ssc.snow.app.gomyways.views.home.controler.menu.notification.ScreenNotificationInMenu;
import ssc.snow.app.gomyways.views.home.controler.menu.payouts.ScreenPayoutsInMenu;
import ssc.snow.app.gomyways.views.home.controler.menu.promotions.ScreenOffers;
import ssc.snow.app.gomyways.views.home.controler.menu.promotions.ScreenRewards;
import ssc.snow.app.gomyways.views.home.controler.menu.wallet.ScreenWallet;
import ssc.snow.app.gomyways.views.home.controler.profile.ScreenEditProfile;
import ssc.snow.app.gomyways.views.login.ScreenLogin;

public class ScreenMenu extends CommonActivity {

    private ReferCodeViewModel mViewModel;
    private String mReferLink = "";


    @SuppressLint("handlerLeak")
    private Handler mApiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 7:
                    dismissIOSProgress();
                    ModelCommon mLoginModel = (ModelCommon) msg.obj;

                    Log.wtf("++", "+++ mModel list size +++" + mLoginModel.getStatus());

                    if (mLoginModel.getStatus()) {
                        getSessionInstance().clearSession();
                        goToLastWithFinishAll(ScreenLogin.class);
                    } else {
                        warningBox(mLoginModel.getMessage());
                    }
                    break;
                case 8:
                    dismissIOSProgress();
                    showToast("failure: " + msg.obj);
                    Log.wtf("error: ", msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_menu);
        ButterKnife.inject(this);

        init();

    }

    private void init() {
        // initialisation
        mViewModel = ViewModelProviders.of(this).get(ReferCodeViewModel.class);


        if (!isNetworkConnected()) return;

        mViewModel.liveResponse(getSessionInstance().getLoggedInUserDetail().getData().getUser().getToken()).observe(this,
                new Observer<ModelReferCode>() {
                    @Override
                    public void onChanged(ModelReferCode it) {
                        // Set the data to the variables

                        if (it != null) {
                            if (it.getStatus()) {
                                mReferLink = it.getData();
                                // Show the data the console
                                Log.wtf("refer", mReferLink);
                            }
                      }
                    }
                });


    }

    @OnClick({R.id.txt_wallet_earning,R.id.txt_email_verification, R.id.img_back, R.id.txt_profile_setting, R.id.txt_verification,
            R.id.txt_notificaiton, R.id.txt_payouts, R.id.txt_bank_details, R.id.txt_help,
            R.id.txt_rewards,
            R.id.txt_offers,
            R.id.txt_refer, R.id.txt_about, R.id.txt_logout,R.id.share_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.txt_profile_setting:
                goForNextScreenWithoutFinish(ScreenEditProfile.class);
                break;
            case R.id.txt_email_verification:
                goForNextScreenWithoutFinish(ScreenEmailAddress.class);
                break;
            case R.id.txt_verification:
                goForNextScreenWithoutFinish(ScreenIdVerification.class);
                break;
            case R.id.txt_notificaiton:
                goForNextScreenWithoutFinish(ScreenNotificationInMenu.class);
                break;
            case R.id.txt_payouts:
                goForNextScreenWithoutFinish(ScreenPayoutsInMenu.class);
                break;
                case R.id.txt_wallet_earning:
                goForNextScreenWithoutFinish(ScreenWallet.class);
                break;
            case R.id.txt_bank_details:
                goForNextScreenWithoutFinish(ScreenBankDetails.class);
                break;
            case R.id.txt_help:
                goForNextScreenWithoutFinish(ScreenHelp.class);
                break;

            case R.id.txt_rewards:
                goForNextScreenWithoutFinish(ScreenRewards.class);
                break;

            case R.id.txt_offers:
                goForNextScreenWithoutFinish(ScreenOffers.class);
                break;

            case R.id.txt_refer:
                if (TextUtils.isEmpty(mReferLink))
                    showToast("Something went wrong! Please try after sometime");
                else
                    getUiHandlerMethod().shareMessage(mReferLink, "");

                break;

            case R.id.share_item:
                if (TextUtils.isEmpty(mReferLink))
                    showToast("Something went wrong! Please try after sometime");
                else
                    getUiHandlerMethod().shareMessage(mReferLink, "");

                break;

            case R.id.txt_about:
                goForNextScreenWithoutFinish(ScreenAbout.class);
                break;
            case R.id.txt_logout:
                logoutDialog("Do you really want to logout?");
                break;
        }
    }

    public void goForLogout(String mUserId) {
        //   String mDeviceToken = getSessionInstanceNotNull().getDeviceFCMToken();
        //    Log.wtf("mDeviceToken== ", mDeviceToken);

        if (!isNetworkConnected()) {
            showToast(getResources().getString(R.string.provide_internet));
            return;
        }

        showIOSProgress();
        getRestfullInstance().logout(mUserId, mApiHandler);
    }


    public void logoutDialog(String msg) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        // Fire api
                        goForLogout(getSessionInstance().getLoggedInUserDetail().getData().getUser().getId());

                    }
                })
                .show();
    }
}
