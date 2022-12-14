package ssc.snow.app.gomyways.data.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.UiHandleMethods;
import ssc.snow.app.gomyways.data.network.ApiRepository;


public abstract class BaseActivity extends AppCompatActivity implements Handler.Callback {

    public Activity _context = null;
    public Handler _handler = null;
    private UiHandleMethods mUihandle;

    private ProgressDialog _progressDlg;
    private Vibrator _vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // this will keep the screen the awake until the app is running
        //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //  check for the permission
        //  addOverlay();

        // lock the home key

        super.onCreate(savedInstanceState);
        mUihandle = new UiHandleMethods(this);
        _context = this;
        _vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        _handler = new Handler(this);

    }

    @NonNull
    public ApiRepository getRestfullInstance() {
        return new ApiRepository();
    }

    @Override
    protected void onDestroy() {

        closeProgress();
        try {
            if (_vibrator != null)
                _vibrator.cancel();
        } catch (Exception e) {
        }
        _vibrator = null;

        super.onDestroy();
    }

    public void showProgress(boolean cancelable) {
        closeProgress();
        _progressDlg = new ProgressDialog(_context, R.style.MyTheme);
        _progressDlg.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        _progressDlg.setCancelable(cancelable);
        _progressDlg.show();

    }

    public void showProgress() {
        showProgress(false);
    }

    public void closeProgress() {

        if (_progressDlg == null) {
            return;
        }
        _progressDlg.dismiss();
        _progressDlg = null;
    }

    public void showAlertDialog(String msg) {

        AlertDialog.Builder customBuilder = new AlertDialog.Builder(_context);

        customBuilder.setTitle(getString(R.string.app_name));
        customBuilder.setMessage(msg);
        customBuilder.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // MyActivity.this.finish();
            }
        });

        //Todo: pending

        // customBuilder.setIcon(R.drawable.a);
        customBuilder.setCancelable(false);
        AlertDialog dialog = customBuilder.create();
        dialog.show();

        Button b = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        if (b != null) {
            b.setBackgroundColor(getResources().getColor(R.color.color_white));
        }
        dialog.show();
    }


    /**
     * show toast
     *
     * @param toast_string
     */
    public void showToast(String toast_string) {
        Toast.makeText(_context, toast_string, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String toast_string) {
        Toast.makeText(_context, toast_string, Toast.LENGTH_LONG).show();
    }

    public void vibrate() {
        if (_vibrator != null)
            _vibrator.vibrate(500);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            default:
                break;
        }
        return false;
    }

    public UiHandleMethods getUiHandlerMethod() {
        return mUihandle;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
