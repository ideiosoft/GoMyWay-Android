package ssc.snow.app.gomyways.views.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.model.ModelLogin;
import ssc.snow.app.gomyways.data.utility.CommonActivity;
import ssc.snow.app.gomyways.data.utility.InjectorUtil;
import ssc.snow.app.gomyways.viewmodel.SocialLoginViewModel;
import ssc.snow.app.gomyways.views.home.ScreenHome;

public class ScreenLogin extends CommonActivity {

    private static final int RC_SIGN_IN = 2001;
    private static final String TAG = "ScreenLogin";
    String mEmail = "", mPwd = "", mLatitude = "0.0", mLongitude = "0.0";

    @InjectView(R.id.email_et)
    EditText emailEt;
    @InjectView(R.id.pass_et)
    EditText passEt;
    @InjectView(R.id.checkbox_remember)
    CheckBox checkboxRemember;

    // Google login
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    // facebook decalarations
    CallbackManager callbackManager;


    private SocialLoginViewModel mViewModelSocial;

    private String loginType = "", mEmailSocial = "", mSocialId = "";


    @SuppressLint("handlerLeak")
    private Handler mApiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 5:
                    dismissIOSProgress();
                    ModelLogin mLoginModel = (ModelLogin) msg.obj;

                    Log.wtf("++", "+++ mLoginModel list size +++" + mLoginModel.getStatus());

                    if (mLoginModel.getStatus()) {

                        if (mLoginModel.getData() != null) {

                            // store user details
                            getSessionInstance().storeLoggedInUserDetail(mLoginModel);

                            // checkbox for the refilled value when it checked the remember field
                            if (checkboxRemember.isChecked()) {
                                // set the remember credentials
                                getSessionInstanceNotNull().setEmailRememberMe(emailEt.getText().toString().trim());
                                getSessionInstanceNotNull().setPwdRememberMe(passEt.getText().toString().trim());
                            }

                            // set the text blank in the test field
                            //  emailEt.setText("");
                            //  passEt.setText("");

                            // if email is not verified
                            if (getSessionInstance().getLoggedInUserDetail().getData().getUser().getEmail_status().equalsIgnoreCase("0")) {
                               // warningBox("Please verify email we have sent link on your registered email!");


                                // after signup
                                getSessionInstanceNotNull().setAfterSignup(true);
                                getSessionInstanceNotNull().setAfterSignupToken(mLoginModel.getData().getUser().getToken());

                                // set the remember credentials
                                // getSessionInstanceNotNull().setEmailRememberMe(emailEt.getText().toString().trim());
                              //   getSessionInstanceNotNull().setPwdRememberMe(passEt.getText().toString().trim());


                                // Send the control to the verification screen
                               goForNextScreen(ActivityLoggedInAfterSignup.class);



                            } else {

                                // Now check for mobile status
                                if (getSessionInstance().getLoggedInUserDetail().getData().getUser().getMobile_status().equalsIgnoreCase("0")) {
                                    goForNextScreenWithoutFinish(ScreenEnterMobileNumber.class);
                                } else {
                                    // Send the control to next move
                                    getSessionInstance().setLoggedIn(true);
                                    goForNextScreen(ScreenHome.class);
                                }
                            }


                        }

                    } else {
                        showToast(mLoginModel.getMessage());
                    }
                    break;
                case 6:
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
        setContentView(R.layout.activity_screen_login);
        ButterKnife.inject(this);
        mViewModelSocial = ViewModelProviders.of(this).get(SocialLoginViewModel.class);
        liveObservesViewModel();
        initSocialLogin();


    }

    private void liveObservesViewModel() {

        mViewModelSocial.liveResponse().observe(this, new Observer<ModelLogin>() {
            @Override
            public void onChanged(ModelLogin mLoginModel) {
                if (mLoginModel != null) {

                    if (mLoginModel.getStatus()) {

                        if (mLoginModel.getData() != null) {

                            // store user details
                            getSessionInstance().storeLoggedInUserDetail(mLoginModel);

                            // set the text blank in the test field
                            //  emailEt.setText("");
                            //  passEt.setText("");

                            // if email is not verified
                            if (getSessionInstance().getLoggedInUserDetail().getData().getUser().getEmail_status().equalsIgnoreCase("0")) {
                                warningBox("Please verify email we have sent link on your registered email!");

                            } else {

                                // Now check for mobile status
                                if (getSessionInstance().getLoggedInUserDetail().getData().getUser().getMobile_status().equalsIgnoreCase("0")) {
                                    goForNextScreenWithoutFinish(ScreenEnterMobileNumber.class);
                                } else {
                                    // Send the control to next move
                                    getSessionInstance().setLoggedIn(true);
                                    goForNextScreen(ScreenHome.class);
                                }
                            }

                        }

                    } else {
                        showToast(mLoginModel.getMessage());
                    }


                }
            }
        });

    }

    private void initSocialLogin() {

        // facebook initialisations
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                setFacebookData(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
                //InjectorUtil.INSTANCE.showToast("Cancel operation");

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.wtf("facebook_error", exception.toString());
                InjectorUtil.INSTANCE.showToast(exception.toString());
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {

            updateUI(account);

        }


    }

    private void setFacebookData(final LoginResult loginResult) {
        // InjectorUtil.INSTANCE.showToast("Working success");

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                // Application code
                // InjectorUtil.INSTANCE.showToast("Suc operation");
                try {
                    Log.wtf("facebook", object.toString());

                    if (object.has("email")) {
                        //      String name = object.optString("name");
                        String id = object.optString("id");
                        String email = object.optString("email");

                        mViewModelSocial.socialLogin(loginType, id, email);

                    } else {
                        // String name = object.optString("name");
                        String id = object.optString("id");

                        mViewModelSocial.socialLogin(loginType, id, "");
                    }

                    if (FacebookSdk.isInitialized()) {
                        LoginManager.getInstance().logOut();
                    }
                } catch (Exception e) {
                    InjectorUtil.INSTANCE.showToast(e.toString());
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,link,birthday,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void updateUI(GoogleSignInAccount acct) {
        // update the ui with the already signed in data

        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            mViewModelSocial.socialLogin(loginType, personId, personEmail);
//         showToast(personEmail + " - " + personName + " - " + personGivenName + " - " + personFamilyName + " - " + personId + " -\n " + personPhoto);

        }

        if (mGoogleSignInClient != null) {
         //   mGoogleSignInClient.asGoogleApiClient().clearDefaultAccountAndReconnect();

            mGoogleSignInClient.revokeAccess();
            mGoogleSignInClient.signOut();

        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        checkboxRemember.setTypeface(getFont("R"));

        // check wehtere the remember me is checked or not
        if (getSessionInstanceNotNull().isRememberMe()) {

            emailEt.setText(getSessionInstanceNotNull().getEmailRememberMe());
            passEt.setText(getSessionInstanceNotNull().getPwdRememberMe());

            checkboxRemember.setChecked(true);

            // set cursor at last in the edit field
            getUiHandlerMethod().setCursorOnLast(passEt, passEt.getText().toString().length());

        } else {
            emailEt.setText("");
            passEt.setText("");
            checkboxRemember.setChecked(false);
        }


    }

    @OnClick({R.id.ll_facebook_click, R.id.ll_google_click, R.id.checkbox_remember,
            R.id.txt_forgot_password, R.id.login_btn, R.id.register_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_facebook_click:

                if (!isNetworkConnected()) {
                    showToast(getResources().getString(R.string.provide_internet));
                    return;
                }
                loginType = "facebook";
                // pending work facebook login setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY).
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

                break;
            case R.id.ll_google_click:

                if (!isNetworkConnected()) {
                    showToast(getResources().getString(R.string.provide_internet));
                    return;
                }
                loginType = "google";
                signIn();
                break;

            case R.id.checkbox_remember:
                // set the flag for the remember  login credentials
                getSessionInstanceNotNull().setRememberMe(checkboxRemember.isChecked());
                break;

            case R.id.txt_forgot_password:
                goForNextScreenWithoutFinish(ScreenForgotPassword.class);
                break;

            case R.id.login_btn:
                // goForNextScreenWithoutFinish(ScreenHome.class);
                if (!checkForEmpty()) {
                    goForLogin(mEmail, mPwd);
                }
                break;

            case R.id.register_now:
                goForNextScreenWithoutFinish(ScreenRegister.class);
                break;


        }
    }

    public boolean checkForEmpty() {
        boolean mFlag = false;

        mEmail = emailEt.getText().toString().trim();
        mPwd = passEt.getText().toString().trim();

        if (TextUtils.isEmpty(mEmail)) {
            emailEt.setError(getResources().getString(R.string.field_cnt_empty));
            mFlag = true;
        } else if (TextUtils.isEmpty(mPwd)) {
            passEt.setError(getResources().getString(R.string.field_cnt_empty));
            mFlag = true;
        }

        return mFlag;
    }

    public void goForLogin(String mEmail, String mPwd) {
        String mDeviceToken = getSessionInstanceNotNull().getDeviceFCMToken();

        Log.wtf("mDeviceToken== ", mDeviceToken);

        if (!isNetworkConnected()) {
            showToast(getResources().getString(R.string.provide_internet));
            return;
        }

        showIOSProgress();
        getRestfullInstance().login(mDeviceToken, mEmail, mPwd, mApiHandler);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (loginType.equalsIgnoreCase("facebook")) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}
