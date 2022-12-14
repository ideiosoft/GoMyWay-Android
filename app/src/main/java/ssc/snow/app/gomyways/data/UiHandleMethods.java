package ssc.snow.app.gomyways.data;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.utility.MyBounceInterpolator;


public class UiHandleMethods {

    private Activity mContext;
    // calendar selection
    private Calendar myCalendar;
    private EditText mEditTextDate;
    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            try {
                updateLabel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };
    private ProgressDialog mProgressDialog;

    public UiHandleMethods(Activity mContext) {
        this.mContext = mContext;
    }

  /*  public ImageLoader imageLoaderrr = ImageLoader.getInstance();

    public UiHandleMethods(Activity mContext) {
        this.mContext = mContext;
        imageLoaderrr.init(ImageLoaderConfiguration.createDefault(mContext));

    }*/

    public static String capitalizeString(String mData) {
        if (mData == null || mData.trim().isEmpty()) {
            return mData;
        }
        char[] c = mData.trim().toLowerCase().toCharArray();
        c[0] = Character.toUpperCase(c[0]);

        return new String(c);

    }

    // get screen width
    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /***
     * format counting in shorter form
     * **/
    public static String numberInShortFormat(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp - 1));
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public void explicitIntent(Class c) {

        mContext.startActivity(new Intent(mContext, c));
        mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    public void startProgress(String msg) {
        mProgressDialog = ProgressDialog.show(mContext, "Please wait", msg, false, false);
    }

    public void stopProgressDialog() {
        if (isShowingDialog()) {
            mProgressDialog.hide();
        }
    }

    public boolean isShowingDialog() {
        return mProgressDialog.isShowing();
    }

    // diff the soft input keypad
    public void hideSoftKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*  public void loadImageWithGlide(String imagePath, CircleImageView imgView) {
        Glide.with(mContext).load(imagePath)
        .crossFade()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgView);
                 } */

    public void setImageWithPicasso(ImageView imageHolder, String path) {
        if (TextUtils.isEmpty(path)) {
            Picasso.with(mContext).load(R.drawable.a)
                    .error(R.drawable.a)
                    .placeholder(R.drawable.a)
                    .fit()
                    .into(imageHolder);
        } else {
            Picasso.with(mContext).load(path)
                    .error(R.drawable.a)
                    .placeholder(R.drawable.a)
                    .fit()
                    .into(imageHolder);
        }
    }

    public void setImageWithPicassoCircle(CircleImageView imageHolder, String path) {
        if (TextUtils.isEmpty(path)) {
            Picasso.with(mContext).load(R.drawable.a)
                    .error(R.drawable.a)
                    .placeholder(R.drawable.a)
                    .fit()
                    .into(imageHolder);
        } else {
            Picasso.with(mContext).load(path)
                    .error(R.drawable.a)
                    .placeholder(R.drawable.a)
                    .fit()
                    .into(imageHolder);
        }
    }

    public void setUnderLine(TextView mTextView) {

        Paint paint = new Paint();
        paint.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTextView.setPaintFlags(paint.getFlags());


    }

    /******
     * load animations for slide up and slide down
     ******/

    public void openWebLink(String webLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webLink));
        // Always use string resources for UI text. This says something like "Share this photo with"
        String title = "Choose one";
        // Create and start the chooser
        Intent chooser = Intent.createChooser(intent, title);
        mContext.startActivity(chooser);
    }

    public void goBack() {
        mContext.finish();
        mContext.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }

    public void goBackFromMiddle() {
        mContext.finish();
        // mContext.overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
        // Hide the Panel
        // Animation bottomDown = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        mContext.overridePendingTransition(R.anim.from_middle, R.anim.to_middle);

    }

    // slide down animation
    public Animation slideDown() {
        return AnimationUtils.loadAnimation(mContext, R.anim.slide_down);
    }

    // slide up animation
    public Animation slideUp() {
        return AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
    }

    public Animation shake() {
        Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
        return shake;
    }

    //       Slide down animation
    public Animation bottomDown() {
        return AnimationUtils.loadAnimation(mContext, R.anim.bottom_down);
    }

    /******
     * Check camera present in system or not
     ******/
    public boolean hasCamera() {
        return mContext.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT);
    }

    /******
     * get Path of Selected image
     ******/
    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        @SuppressWarnings("deprecation")
        Cursor cursor = mContext.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    // calendar setup method
    public void getCalendarDialogDate(EditText mEditTextDate) {
        this.mEditTextDate = mEditTextDate;

        // Calendar intialisations
        myCalendar = Calendar.getInstance();
        new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar
                .get(Calendar.MONTH), myCalendar
                .get(Calendar.DAY_OF_MONTH)).show();

        //  for desable previous date setting
        //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        //  datePickerDialog.show();

    }

    // update calendar with format
    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; // dd/MM/yyyyIn which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEditTextDate.setText(sdf.format(myCalendar.getTime()));
    }

    // filter numbers only from symbol mixed String
    public String filterNumberFromFormattedString(String num) {
        String finalNumber = "".trim();
        for (int index = 0; index < num.length(); index++) {
            if (Character.isDigit(num.charAt(index))) {
                finalNumber = finalNumber + num.charAt(index);

            }

        }

        return finalNumber;
    }



    // send email on desired Address
    public void sendEmail(String emailToAddress) {
        /*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "some@email.address" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        mContext.startActivity(Intent.createChooser(intent, ""));*/

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + emailToAddress + "?subject=" + "" + "&body=" + "");
        intent.putExtra(Intent.EXTRA_CC, "");
        intent.setData(data);
        mContext.startActivity(Intent.createChooser(intent, ""));
    }

    // send email on desired Address
    public void sendEmailFromTo(String emailCCAddress, String emailToAddress) {
        /*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "some@email.address" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        mContext.startActivity(Intent.createChooser(intent, ""));*/

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + emailToAddress + "?subject=" + "" + "&body=" + "");
        intent.putExtra(Intent.EXTRA_CC, emailCCAddress);
        //  intent.putExtra(Intent.EXTRA_REPLACEMENT_EXTRAS)
        intent.setData(data);
        mContext.startActivity(Intent.createChooser(intent, ""));
    }

    //  set tabs with on activity Magic tabs
    // dialog messages with the types

    // change date format
    public String changeDateFormat(String incomingDate) {
        // parse the String "29/07/2013" to a java.util.Date object
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-mm-dd").parse(incomingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // format the java.util.Date object to the desired format
        return new SimpleDateFormat("MM/dd/yy").format(date);

    }

    // set cursor after last character in the edittext on page loading
    public void setCursorOnLast(EditText editText, int length) {

        Selection.setSelection(editText.getText(), length);

    }

    // first letter of word is slightly bigger than other words
    public void makeFirstLetterSpannable(String title, TextView titleTextView) {

        final SpannableString spannableString = new SpannableString(title);
        String[] splitString = title.split("\\s+");
        char[] splitStringFirstChartacter = new char[splitString.length];
        int[] positions = new int[splitString.length];

        for (int i = 0; i < splitString.length; i++) {

            splitStringFirstChartacter[i] = splitString[i].charAt(0);
            if ((splitStringFirstChartacter[i] >= 'a' && splitStringFirstChartacter[i] <= 'z') || (splitStringFirstChartacter[i] >= 'A' && splitStringFirstChartacter[i] <= 'Z') || (splitStringFirstChartacter[i] >= '0' && splitStringFirstChartacter[i] <= '9')) {
                positions[i] = title.indexOf(splitStringFirstChartacter[i]);
            }

            spannableString.setSpan(new RelativeSizeSpan(1.5f), positions[i], positions[i] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        titleTextView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    // first letter of word is slightly bigger than other words
    public void makeFirstLetterSpannableOnButton(String title, TextView titleTextView) {

        final SpannableString spannableString = new SpannableString(title);
        String[] splitString = title.split("\\s+");
        char[] splitStringFirstChartacter = new char[splitString.length];
        int[] positions = new int[splitString.length];

        for (int i = 0; i < splitString.length; i++) {

            splitStringFirstChartacter[i] = splitString[i].charAt(0);
            if ((splitStringFirstChartacter[i] >= 'a' && splitStringFirstChartacter[i] <= 'z') || (splitStringFirstChartacter[i] >= 'A' && splitStringFirstChartacter[i] <= 'Z') || (splitStringFirstChartacter[i] >= '0' && splitStringFirstChartacter[i] <= '9')) {
                positions[i] = title.lastIndexOf(splitStringFirstChartacter[i]);
            }

            spannableString.setSpan(new RelativeSizeSpan(1.5f), positions[i], positions[i] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        titleTextView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    //       Show arrow o top left corner of screen_home
    public void setDisplayHomeAsUpArrow(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(mContext.getResources().getDrawable(R.drawable.a));
    }


    public void cameraIntent(int code) {
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  startActivityForResult(cameraintent, 101);
        mContext.startActivityForResult(Intent.createChooser(cameraintent, "Click Picture"), code);
    }

    //      Refresh activity
    public void refresh() {
        mContext.finish();
        mContext.overridePendingTransition(0, 0);
        mContext.startActivity(mContext.getIntent());
        mContext.overridePendingTransition(0, 0);

    }

    public void shareMessage(String shareBody, String mPhone) {
        if (mPhone.trim().isEmpty()) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "GoMyWays");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            mContext.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mPhone + ""));
            //    smsIntent.setType("text");
            smsIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            //      smsIntent.setPackage("com.whatsapp");
            mContext.startActivity(Intent.createChooser(smsIntent, "Share Using"));


        }
    }

    public void shareMessageUsingSMSApp(String mMsg) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        //   smsIntent.putExtra("address", "12125551212");
        smsIntent.putExtra("sms_body", mMsg);
        mContext.startActivity(smsIntent);
    }

    public void sendSMS(String mMsg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(mContext); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, mMsg);

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            mContext.startActivity(sendIntent);

        } else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            // smsIntent.putExtra("address", "phoneNumber");
            smsIntent.putExtra("sms_body", mMsg);
            mContext.startActivity(smsIntent);
        }
    }

    public void shareImage(File path) {

        Intent share = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.fromFile(path);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        mContext.startActivity(Intent.createChooser(share, "Share Image to"));
    }

    public String getImagePath(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;

    }

    public String getVideoPath(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Video.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;

    }

    public String getAudioPath(Intent data) {

        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Audio.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();

        return path;

    }

    public void appendTextView(String Value, LinearLayout mLinear) {

        TextView msg = new TextView(mContext);
        msg.setText(Value);
        msg.setPadding(10, 10, 10, 10);
        msg.setTextColor(mContext.getResources().getColor(R.color.color_white));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 15, 0, 0);
        params.gravity = Gravity.LEFT;
        msg.setLayoutParams(params);
        msg.setGravity(Gravity.CENTER);
        mLinear.addView(msg);
    }

    //  show Toast
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }

    public void goForNext(Class cl) {
        mContext.startActivity(new Intent(mContext, cl));
        mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        mContext.finish();
    }

    public void goToNextWithClearBackStack(Class cls) {
        Intent i = new Intent(mContext, cls);
        // set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(i);
        mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        mContext.finish();

    }

    public void goForNextScreen(Class c) {

        mContext.startActivity(new Intent(mContext, c));
        mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }


    //Check if internet is present or not
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public Animation bounceAnimationOnButton() {
        final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);

        return myAnim;
    }

    public void phoneNumberFormat(final EditText mEditTextPhoneNumber) {
        mEditTextPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            //we need to know if the user is erasing or inputing some new character
            private boolean backspacingFlag = false;
            //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
            private boolean editedFlag = false;
            //we need to mark the cursor position and restore it after the edition
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //we store the cursor local relative to the end of the string in the EditText before the edition
                cursorComplement = s.length() - mEditTextPhoneNumber.getSelectionStart();
                //we check if the user ir inputing or erasing a character
                backspacingFlag = count > after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing to do here =D
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                //what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                String phone = string.replaceAll("[^\\d]", "");

                //if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
                //if the flag is false, this is a original user-typed entry. so we go on and do some magic
                if (!editedFlag) {

                    //we start verifying the worst case, many characters mask need to be added
                    //example: 999999999 <- 6+ digits already typed
                    // masked: (999) 999-999
                    if (phone.length() >= 6 && !backspacingFlag) {
                        //we will edit. next call on this textWatcher will be ignored
                        editedFlag = true;
                        //here is the core. we substring the raw digits and add the mask as convenient
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
                        mEditTextPhoneNumber.setText(ans);
                        //we deliver the cursor to its original position relative to the end of the string
                        mEditTextPhoneNumber.setSelection(mEditTextPhoneNumber.getText().length() - cursorComplement);

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3);
                        mEditTextPhoneNumber.setText(ans);
                        mEditTextPhoneNumber.setSelection(mEditTextPhoneNumber.getText().length() - cursorComplement);
                    }
                    // We just edited the field, ignoring this cicle of the watcher and getting ready for the next
                } else {
                    editedFlag = false;
                }
            }
        });
    }

    public void changeColorToAppGradient(TextView mTextKofefe) {
        int[] color = {ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimaryDark, null), ResourcesCompat.getColor(mContext.getResources(), R.color.colorPrimary, null)};
        //without theme
        float[] position = {0, 1};
        Shader.TileMode tile_mode0 = Shader.TileMode.REPEAT; // or TileMode.REPEAT;
        LinearGradient lin_grad0 = new LinearGradient(0, 0, 0, 100, color, position, tile_mode0);
        Shader shader_gradient0 = lin_grad0;

        mTextKofefe.getPaint().setShader(shader_gradient0);

    }

    public void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public void setStatusBarColor(int color) {

        Window window = mContext.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(mContext.getResources().getColor(color));
        }


    }

    public void setStatusBarGradiant() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mContext.getWindow();
            Drawable background = mContext.getResources().getDrawable(R.drawable.a);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mContext.getResources().getColor(R.color.color_transparent));
            window.setNavigationBarColor(mContext.getResources().getColor(R.color.color_transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public void loadImageWithGlideBitmap(ImageView mmImageView, String mUrlPath) {
        Glide.with(mContext)
                .asBitmap()
                .load(mUrlPath)
                .into(mmImageView);

        // ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
// Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
//	which implements ImageAware interface)
        //       imageLoaderrr.displayImage(mUrlPath, mmImageView);

    }

    public Bitmap downloadImageUrl(String strUrl) throws IOException {
        Bitmap bitmap = null;
        InputStream iStream = null;
        try {
            URL url = new URL(strUrl);
            /** Creating an http connection to communcate with url */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            /** Connecting to url */
            urlConnection.connect();

            /** Reading data from url */
            iStream = urlConnection.getInputStream();

            /** Creating a bitmap from the stream returned from the url */
            bitmap = BitmapFactory.decodeStream(iStream);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
        }
        return bitmap;
    }

    public Bitmap loadBitmapFromView(View v) {
        if (v.getMeasuredHeight() <= 0) {
            v.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }


    public Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }


    public void videoSetUp(VideoView mVideoView, String path) {
        //   Uri video = Uri.parse("http://www.servername.com/projects/projectname/videos/1361439400.mp4");
        //   mVideoView.setVideoURI(video);
        try {
            mVideoView.setVideoPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaController mc = new MediaController(mContext);

        mVideoView.setMediaController(mc);
        mVideoView.start();
        //    mc.show(0);
        mc.setEnabled(true);

        mVideoView.requestFocus();

    }


    //   filter video from url to display in the videoView
    public void getVideoFromUrl(final String pathVideo, final VideoView mVideoView) {
        class UploadVideo extends AsyncTask<Void, Void, String> {
            /*
            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);
                try {
                    if (mKProgressHUD.isShowing()) {
                        mKProgressHUD.dismiss();
           }*/

            private KProgressHUD mKProgressHUD;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mKProgressHUD = KProgressHUD.create(mContext)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setDetailsLabel("Buffering...")
                        .setCancellable(true)
                        .setAnimationSpeed(1)
                        .setDimAmount(.2f)
                        .show();

            }

            @Override
            protected void onPostExecute(final String s) {
                super.onPostExecute(s);
                try {
                    if (mKProgressHUD.isShowing()) {
                        mKProgressHUD.dismiss();
                    }
                    if (s != null) {
                        // jetPlay(s);
                        videoSetUp(mVideoView, s);


                    }
                } catch (Exception e) {
                    if (mKProgressHUD.isShowing()) {
                        mKProgressHUD.dismiss();
                    }
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    return getDataSource(pathVideo);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }

    // Datasource for the video to view
    private String getDataSource(String path) throws IOException {
        if (!URLUtil.isNetworkUrl(path)) {
            return path;
        } else {
            URL url = new URL(path);
            URLConnection cn = url.openConnection();
            cn.connect();
            InputStream stream = cn.getInputStream();
            if (stream == null)
                throw new RuntimeException("stream is null");
            File temp = File.createTempFile("mediaplayertmp", "dat");
            temp.deleteOnExit();
            String tempPath = temp.getAbsolutePath();
            FileOutputStream out = new FileOutputStream(temp);
            byte[] buf = new byte[128];
            do {
                int numread = stream.read(buf);
                if (numread <= 0)
                    break;
                out.write(buf, 0, numread);
            } while (true);
            try {
                stream.close();
            } catch (IOException ex) {
                Log.e("TAG", "error: " + ex.getMessage(), ex);
            }
            return tempPath;
        }
    }

    public class CheckForSDCard {
        //Check If SD Card is present or not method
        public boolean isSDCardPresent() {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }
    }


}
