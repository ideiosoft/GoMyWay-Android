package ssc.snow.app.gomyways.data.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.CheckConnectivity;
import ssc.snow.app.gomyways.data.session.SessionGomyway;
import ssc.snow.app.gomyways.data.session.SessionNotNull;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static ssc.snow.app.gomyways.data.utility.Constants.SELECT_FILE;


public abstract class CommonActivity extends BaseActivity {

    public static final int REQUEST_SCANNER_CODE = 101;
    public static final int REQUEST_ACTIVITY_CODE = 4321;

    public static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    // ########## Constant field for permission request ###########
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_PERMISSION = 101;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MEDIA_TYPE_VIDEO = 202;
    public static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1));
    public static final List<String> timesString = Arrays.asList("year", "month", "day", "hour", "minute", "second");
    //         Messages
    public String ERROR_EMPTY_JSON = "Something went wrong! Please check internet connection and retry again";
    public String NETWORK_ERROR = "Internet not found";

    public int PLACE_PICKER_REQUEST = 221;

    protected Uri photoURI;
    protected File photoFile = null;
    protected File mediaFile;
    private ProgressDialog mDialog = null;
    private KProgressHUD mKProgressHUD, mKProgressHUDWithText;
    //    Record video in mp4 format
    private Uri fileUri;
    private String mCurrentPhotoPath = "";
    private String mDataTimeFormat = "yyyy-MM-dd hh:mm:ss"; // dd/MM/yyyyIn which you need put here
    protected SimpleDateFormat sdfDateTimeForApi = new SimpleDateFormat(mDataTimeFormat, Locale.US);
    private String myFormat = "dd-MM-yyyy"; // dd/MM/yyyyIn which you need put here
    protected SimpleDateFormat sdfDate = new SimpleDateFormat(myFormat, Locale.US);
    private String myFormatTime = "hh:mma"; // dd/MM/yyyyIn which you need put here
    protected SimpleDateFormat sdfTime = new SimpleDateFormat(myFormatTime, Locale.US);
    /**
     * Here is the key method to apply the animation
     **/

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private String mAddress = "", country = "", city = "", state = "", pincode = "";

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {

        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {

        // Check that the SDCard is mounted
        File mediaStorageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
        Log.d("path", mediaStorageDir + "");

        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // ("Failed to create directory MyCameraVideo.");
                Log.d("MyCameraVideo", "Failed to create directory MyCameraVideo.");
                return null;
            }
        }
        // Create a media file name

        // For unique file name appending current timeStamp with file name
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date.getTime());
        File mediaFile;

        if (type == MEDIA_TYPE_VIDEO) {
            // For unique video file name appending current timeStamp with file name
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
            //send broadcast to gallery to show new incomimng data

        } else {
            return null;
        }
        return mediaFile;
    }

    public static String displayQuoteTime(long time_ago) {
        try {

            time_ago = time_ago;
            long cur_time = (Calendar.getInstance().getTimeInMillis()) / 1000;
            long time_elapsed = cur_time - time_ago;
            long seconds = time_elapsed;
            // Seconds
            if (seconds <= 60) {
                return "Just now";
            }
            //Minutes
            else {
                int minutes = Math.round(time_elapsed / 60);

                if (minutes <= 60) {
                    if (minutes == 1) {
                        return "a min ago";
                    } else {
                        return minutes + " mins ago";
                    }
                }
                //Hours
                else {
                    int hours = Math.round(time_elapsed / 3600);
                    if (hours <= 24) {
                        if (hours == 1) {
                            return "An hour ago";
                        } else {
                            return hours + " hrs ago";
                        }
                    }
                    //Days
                    else {
                        int days = Math.round(time_elapsed / 86400);
                        if (days <= 7) {
                            if (days == 1) {
                                return "Yesterday";
                            } else {
                                return days + " days ago";
                            }
                        }
                        //Weeks
                        else {
                            int weeks = Math.round(time_elapsed / 604800);
                            if (weeks <= 4.3) {
                                if (weeks == 1) {
                                    return "A week ago";
                                } else {
                                    return weeks + " weeks ago";
                                }
                            }
                            //Months
                            else {
                                int months = Math.round(time_elapsed / 2600640);
                                if (months <= 12) {
                                    if (months == 1) {
                                        return "A month ago";
                                    } else {
                                        return months + " months ago";
                                    }
                                }
                                //Years
                                else {
                                    int years = Math.round(time_elapsed / 31207680);
                                    if (years == 1) {
                                        return "One year ago";
                                    } else {
                                        return years + " years ago";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getlongtoago(long createdAt) {
        DateFormat userDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        DateFormat dateFormatNeeded = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS");
        Date date = null;
        date = new Date(createdAt);
        String crdate1 = dateFormatNeeded.format(date);

        // Date Calculation
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        crdate1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);

        // get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currenttime = dateFormat.format(cal.getTime());

        Date CreatedAt = null;
        Date current = null;
        try {
            CreatedAt = dateFormat.parse(crdate1);
            current = dateFormat.parse(currenttime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = current.getTime() - CreatedAt.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String time = null;
        if (diffDays > 0) {
            if (diffDays == 1) {
                time = diffDays + "day ago ";
            } else {
                time = diffDays + "days ago ";
            }
        } else {
            if (diffHours > 0) {
                if (diffHours == 1) {
                    time = diffHours + "hr ago";
                } else {
                    time = diffHours + "hrs ago";
                }
            } else {
                if (diffMinutes > 0) {
                    if (diffMinutes == 1) {
                        time = diffMinutes + "min ago";
                    } else {
                        time = diffMinutes + "mins ago";
                    }
                } else {
                    if (diffSeconds > 0) {
                        time = diffSeconds + "secs ago";
                    }
                }

            }

        }
        return time;
    }

    public static String toDuration(long duration) {

        StringBuffer res = new StringBuffer();

        for (int i = 0; i < CommonActivity.times.size(); i++) {
            Long current = CommonActivity.times.get(i);
            long temp = duration / current;
            if (temp > 0) {
                res.append(temp).append(" ").append(CommonActivity.timesString.get(i))
                        .append(temp != 1 ? "s" : "").append(" ago");
                break;
            }
        }
        if ("".equals(res.toString()))
            return "0 seconds ago";
        else
            return res.toString();
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
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

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 250);
        return noOfColumns;
    }
/*

    protected void goForPlacePickerDialog() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
*/

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "see less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. read more", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public ArrayList<String> getGenderList() {
        ArrayList<String> mListGender = new ArrayList<>();
        mListGender.add("Male");
        mListGender.add("Female");
        mListGender.add("Other");
        return mListGender;
    }

    public HashMap<String, String> getGenderValue() {
        // Attendance values for sending hash map
        HashMap<String, String> mHashGender = new HashMap<>();

        mHashGender.put("Male", "m");
        mHashGender.put("Female", "f");
        mHashGender.put("Other", "o");

        return mHashGender;
    }

    public ArrayList<String> getUptoFiveDialer() {
        ArrayList<String> mList = new ArrayList<>();
        mList.add("1");
        mList.add("2");
        mList.add("3");
        mList.add("4");
        mList.add("5");

        return mList;

    }

    // choose image dialog from Gallary and Camera
    protected void Image_Picker_Dialog() {
        //      AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogSlideAnim));
        System.gc();
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setMessage("Choose");
        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //      call gallary intent
                galleryIntent();
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                //      calling camera intent
                cameraIntent();
            }
        });

        myAlertDialog.show();

    }

    // Choose image from gallery
    protected void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_FILE);     // one can be replaced with any action code

    }

    // click image from camera
    protected void cameraIntent() {
        Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.d("mylog", "Exception while creating file: " + ex.toString());
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Log.d("mylog", "Photofile not null");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", photoFile);
            } else {
                photoURI = Uri.parse("file:" + photoFile.getAbsolutePath());
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, Constants.TAKE_PICTURE);
        }
        //   startActivityForResult(intent, Constants.TAKE_PICTURE); //zero can be replaced with any action code

    }

    private File createImageFile() throws IOException {
        //       Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("mylog", "Path: " + mCurrentPhotoPath);
        return image;
    }

    // pick a audio from android
    protected void audioIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, Constants.PICK_SONG);     // one can be replaced with any action code

      /*  Intent intent_upload = new Intent();
        intent_upload.setType("audio*//*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload,Constants.PICK_SONG);*/
    }

    protected void Video_Picker_Dialog() {
        //      AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogSlideAnim));
        System.gc();
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setMessage("Choose");
        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                galleryVideoIntent();
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                fireCaptureVideo();

            }
        });

        myAlertDialog.show();

    }

    // record video from camera
    public void fireCaptureVideo() {

        Intent videoCaptureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        videoCaptureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", mediaFile);
        } else {
            fileUri = Uri.parse("file:" + mediaFile.getAbsolutePath());
        }
        // create a file to save the video
        //  fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

        // set video quality
        videoCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        // videoCaptureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 10 * 1024 * 1024);
        videoCaptureIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 59); // upto 59 second this video will be recorded

        // set the image file name
        videoCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (videoCaptureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(videoCaptureIntent, Constants.RECORD_VIDEO_);
        }

    }

    //    Choose image from gallery
    protected void galleryVideoIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, Constants.REQUEST_TAKE_GALLERY_VIDEO);

    }

    public String numberInShortFormat(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp - 1));
    }

    protected void onPlaySongWithInbuiltMusicApps() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri data = Uri.parse("file:///sdcard/Music");
        String type = "audio/mp3";
        intent.setDataAndType(data, type);
        startActivityForResult(intent, Constants.PICK_SONG);

    }
    // public SessionForProfile getSessionForProfileInstance() {
    // return new SessionForProfile(this);
    //  }

    public String getLanguage() {

        Locale systemLocale = getResources().getConfiguration().locale;
        String strLanguage = systemLocale.getLanguage();

        return strLanguage;
    }

    public void byPassCall(Class c) {
        startActivity(new Intent(this, c));
    }

    public void showProgressDialog(String message) {
        mDialog = ProgressDialog.show(this, "Please Wait", message, false, false);
    }

    public void dismissProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public SessionGomyway getSessionInstance() {
        return new SessionGomyway(this);
    }

    public SessionNotNull getSessionInstanceNotNull() {
        return new SessionNotNull(this);
    }

    public void showIOSProgress() {
        try {
            mKProgressHUD = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    //  .setLabel("Please wait")
                    //  .setDetailsLabel(msg)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(.2f)
                    .show();
        } catch (Exception ex) {
            Log.wtf("IOS_error_starting", ex.getCause());
        }

    }

    public void dismissIOSProgress() {
        try {
            if (mKProgressHUD != null) {
                if (mKProgressHUD.isShowing()) {
                    mKProgressHUD.dismiss();
                }
            }
        } catch (Exception ex) {
            Log.wtf("IOS_error_dismiss", ex.getCause());
        }


    }

    public void showIOSProgressWithText(String msg) {
        mKProgressHUDWithText = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel(msg)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(.2f)
                .show();
    }

    public void dismissIOSProgressWithText() {
        if (mKProgressHUDWithText.isShowing()) {
            mKProgressHUDWithText.dismiss();
        }
    }

    public String refinePhoneNumber(String mFormatedPhone) {
        return mFormatedPhone.replaceAll("[-.^:(,)]", "").replaceAll(" ", "");
    }

    public String refineNumberFromExtraSymbols(String num) {
        return num.replaceAll("[-+.^:(,)]", "").replaceAll(" ", "");
    }

    public boolean isNetworkConnected() {
        return new CheckConnectivity(this).isNetworkAvailable();
    }

    public Bitmap getBitmapFromView(ImageView mView) {
        try {
            if (mView != null && mView.getDrawable() != null) {
                return ((BitmapDrawable) mView.getDrawable()).getBitmap();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return null;

    }

    public void generateHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "ssc.snow.app.gomyways", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getDataTimeFromMillisecondsForReacted(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        SimpleDateFormat f = new SimpleDateFormat("MMM");

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        int mHour = calendar.get(Calendar.HOUR);
        int mMin = calendar.get(Calendar.MINUTE);
        int mSecond = calendar.get(Calendar.SECOND);

        return "" + mDay + ", " + getMonthForInt(mMonth);

    }

    public String getDataTimeFromMilliseconds(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        SimpleDateFormat f = new SimpleDateFormat("MMM");

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        int mHour = calendar.get(Calendar.HOUR);
        int mMin = calendar.get(Calendar.MINUTE);
        int mSecond = calendar.get(Calendar.SECOND);

        return "" + getMonthForInt(mMonth) + ", " + mYear;

    }

    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month.replace(month.substring(3), "");
    }

    public String getRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void setImageWithPicasso(ImageView imageHolder, String path, int mImgOffline) {
        if (TextUtils.isEmpty(path)) {
            Picasso.with(this).load(mImgOffline)
                    .error(mImgOffline)
                    .placeholder(mImgOffline)
                    .fit()
                    .into(imageHolder);
        } else {
            Picasso.with(this).load(path)
                    .error(mImgOffline)
                    .placeholder(mImgOffline)
                    .fit()
                    .into(imageHolder);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public AlphaAnimation getBlinkingAnimation() {
        AlphaAnimation blinkanimation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        blinkanimation.setDuration(300); // duration
        blinkanimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        blinkanimation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        blinkanimation.setRepeatMode(Animation.REVERSE);
        return blinkanimation;
    }

    public void goForNext(Class cl) {
        startActivity(new Intent(this, cl));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        finish();
    }

    public void goToNextWithClearBackStack(Class cls) {
        Intent i = new Intent(this, cls);
        // set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        finish();

    }

    public void goBack() {
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }

    protected Animation bounceAnimationOnButton() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);

        return myAnim;
    }

    public void hideSystemBottomBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public void goForNextScreenWithoutFinish(Class mClass) {

        startActivity(new Intent(this, mClass));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void showSnackBar(View v, String mMsg) {
        Snackbar.make(v, mMsg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    protected void animationToItemsFadeIn(final RecyclerView mRecyclerView) {
        mRecyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                            View v = mRecyclerView.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.animate().alpha(1.0f)
                                    .setDuration(300)
                                    .setStartDelay(i * 50)
                                    .start();
                        }

                        return true;
                    }
                });
    }

    public void goForNextScreen(Class mClass) {

        Intent i = new Intent(this, mClass);
        startActivity(i);
        this.finish();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void goForHomeFromLeftToRight(Class mClass) {
        startActivity(new Intent(this, mClass));
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void goToLastWithFinishAll(Class mClass) {
        startActivity(new Intent(this, mClass));
        this.finishAffinity();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void goForHomeFromRightToLeft(Class mClass) {
        startActivity(new Intent(this, mClass));
        this.finish();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void warningBox(String msg) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Warning")
                .setContentText(String.valueOf(Html.fromHtml(msg)))
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                    }
                })
                .show();
    }

    // ###### send the class name and return object of that class
    public Object mGiveMeBackWhatYouIGave(Class mClass) {
        try {
            Object object = Class.forName(mClass.getName()).getConstructor().newInstance();
            return object;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public long convertDateIntoMilliseconds(String mDate) {
        try {  //2018-04-26 10:28:04"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = null;
            date = sdf.parse(mDate);

            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            return Long.parseLong(null);
        }

    }

    public void removeStatusBar() {
        // ##########################
        // Remove top system bar
        // ##########################
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /*  public void updateCameraBearing(GoogleMap googleMap, float bearing) {
          if (googleMap == null) return;
          CameraPosition camPos = CameraPosition
                  .builder(
                          googleMap.getCameraPosition() // current Camera
                  )
                  .bearing(bearing)
                  .build();
          googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
      }
  */
    // apply font to the item menu in the navigation view in the home screen
    public void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void strikeThroughTextView(TextView mTextView) {
        mTextView.setPaintFlags(mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    /******
     * Check camera present in system or not
     ******/
    public boolean hasCamera() {
        return this.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT);
    }

    // diff the soft input keypad
    public void hideSoftKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // set cursor after last character in the edittext on page loading
    public void setCursorOnLast(EditText editText, int length) {

        Selection.setSelection(editText.getText(), length);


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

    public String getGeoAddressFromLatLong(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            //   String knownName = addresses.get(0).getFeatureName(); // Only if available else return

            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }


    }

    //      Refresh activity
    public void refreshScreen(Activity mContext) {
        mContext.finish();
        mContext.overridePendingTransition(0, 0);
        mContext.startActivity(mContext.getIntent());
        mContext.overridePendingTransition(0, 0);

    }

    public void loadImageWithGlideBitmap(CircleImageView mmImageView, String mUrlPath) {
        Glide.with(this)
                .asBitmap()
                .placeholder(R.color.grey_bg_clr)
                .load(mUrlPath)
                .into(mmImageView);

    }

    public void loadImageWithGlideBitmap(ImageView mmImageView, String mUrlPath) {
        Glide.with(this)
                .asBitmap()
                .load(mUrlPath)
                .into(mmImageView);

    }

    // Hide status bar in the activity
    public void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    public void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setStatusBarColor(int color) {

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(color));
        }


    }

    public void showImage(int imageUri) {
        try {
            Dialog builder = new Dialog(this);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //nothing;
                }
            });

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageUri);
            builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            builder.show();
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }

    protected boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public void shareMessageUsingSMSApp(String mMsg) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        //   smsIntent.putExtra("address", "12125551212");
        smsIntent.putExtra("sms_body", mMsg);
        startActivity(smsIntent);
    }

    public String getHtmlText(String mData) {

        if (mData != null) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                return Html.fromHtml(mData).toString();
            } else {
                return Html.fromHtml(mData, Html.FROM_HTML_MODE_LEGACY).toString();
            }
        } else
            return "";


    }

    public void getTimeDialog(final EditText mEdtTime) {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int mins) {

                // Incase check for the time comparison according to current date (Uncomment below code)
/*

                Calendar datetime = Calendar.getInstance();
                Calendar calendar = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hours);
                datetime.set(Calendar.MINUTE, mins);

                if (datetime.getTimeInMillis() >= calendar.getTimeInMillis()) {

                } else {
                    Toast.makeText(CommonActivity.this, "Invalid Time", Toast.LENGTH_LONG).show();
                    return;
                }
*/


                String timeSet = "";
                if (hours > 12) {
                    hours -= 12;
                    timeSet = "PM";
                } else if (hours == 0) {
                    hours += 12;
                    timeSet = "AM";
                } else if (hours == 12)
                    timeSet = "PM";
                else
                    timeSet = "AM";


                String minutes = "";
                if (mins < 10)
                    minutes = "0" + mins;
                else
                    minutes = String.valueOf(mins);

                // Append in a StringBuilder
                String aTime = new StringBuilder().append(hours).append(':')
                        .append(minutes).append(" ").append(timeSet).toString();

                mEdtTime.setText(aTime);


            }
        }, hour, minute, false);//Yes 24 hour time


        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public Typeface getFont(String mA) {

        switch (mA) {
            case "T":
                return Typeface.createFromAsset(getAssets(), "fonts/thin.ttf");

            case "R":
                return Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
            case "M":
                return Typeface.createFromAsset(getAssets(), "fonts/med.ttf");

            case "RB":
                return Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

            case "RL":
                return Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");


            default:
                return null;


        }


    }

    public String calculateFileSize(String filepath) {
        //String filepathstr=filepath.toString();
        File file = new File(filepath);
        float fileSizeInBytes = file.length();

        float fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        float fileSizeInMB = fileSizeInKB / 1000;

        String calString = Float.toString(fileSizeInMB);
        return calString;
    }

    public String roundOffTo2DecPlaces(float val) {
        return String.format("%.2f", val);
    }

    public void gotoGoogleMap(String mLocationName) {

        String map = "http://maps.google.co.in/maps?q=" + mLocationName;
        //  String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(intent);
    }

    public void getGenderPopup(List<String> mListGende, final EditText mEditText_) {
        @SuppressLint({"NewApi", "LocalSuppress"}) PopupMenu popup = new PopupMenu(this, mEditText_, Gravity.TOP);
        //Inflating the Popup using xml file
        //popup.getMenuInflater().inflate(R.menu.menu_comment, popup.getMenu());

        for (String mData : mListGende) {
            popup.getMenu().add(mData.trim());
        }

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                mEditText_.setText(item.getTitle().toString());

                return true;
            }
        });

        popup.show();

    }

    public String getRefineMessage(String mMSG) {
        String mFinalMSg = "";
        if (!TextUtils.isEmpty(mMSG)) {

            String[] mFactor = mMSG.split(".,");
            for (int i = 0; i < mFactor.length; i++) {
                mFinalMSg = mFinalMSg + mFactor[i] + "\n";
            }

            return mFinalMSg;
        } else {
            return mFinalMSg;
        }


    }


    public String getFcmToken() {
        String deviceToken = getSessionInstanceNotNull().getDeviceFCMToken();

        if (deviceToken != null) {
            return deviceToken;
        } else {
            Log.wtf("msg", "Firebase Reg Id is not received yet!");
            return "";
        }
    }

    public void getPlaceDetailName(double lt1, double ln1, EditText mEditTextAddress) {
        Geocoder geocoder = new Geocoder(this);

        // remove all non numeric character from the float values
        //  str.replaceAll("[^\\d.]", "");

        String mLt1 = String.valueOf(lt1).replaceAll("[^\\d.]", "");
        String mLn1 = String.valueOf(ln1).replaceAll("[^\\d.]", "");

        // refined lat lon
        lt1 = Double.parseDouble(mLt1);
        ln1 = Double.parseDouble(mLn1);

        try {

            List<Address> addresses = geocoder.getFromLocation(lt1, ln1, 1);
            if (addresses != null && addresses.size() > 0) {

                for (Address address : addresses) {
                    // uihandle.showToast("" + address);
                    Log.i("info", address.toString());
                }

                if (addresses.get(0).getCountryName() != null) {
                    country = addresses.get(0).getCountryName();
                }
                if (addresses.get(0).getAddressLine(0) != null) {
                    mAddress = addresses.get(0).getAddressLine(0);
                }
                if (addresses.get(0).getAdminArea() != null) {
                    state = addresses.get(0).getAdminArea();
                }
                if (addresses.get(0).getPostalCode() != null) {
                    pincode = addresses.get(0).getPostalCode();
                }

                if (addresses.get(0).getSubAdminArea() != null) {
                    city = addresses.get(0).getSubAdminArea();
                } else if (addresses.get(0).getLocality() != null)
                    city = addresses.get(0).getLocality();

                // set the values to the views from lat long
                mEditTextAddress.setText(city);
                getUiHandlerMethod().setCursorOnLast(mEditTextAddress, mEditTextAddress.length());

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Here getting distance in kilometers (km)
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    // distance in the meter
    public String getDistanceBetweenLatLng(Location startPoint, Location endPoint) {

        double distance = startPoint.distanceTo(endPoint);
        return String.format("%.2f", distance);
    }

   /* public void clickToCopy(String mData) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", mData);
        clipboard.setPrimaryClip(clip);

        // Show display after successfully
        showToast("Copied!");

    }*/

    public void clickToCopy(String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }

        // Show display after successfully
        showToast("Copied!");
    }

    public void getAddressFromLocation(final String locationAddress, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(CommonActivity.this, Locale.getDefault());
                String result = null;
                try {
                    List
                            addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = (Address) addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(address.getLatitude()).append("\n");
                        sb.append(address.getLongitude()).append("\n");
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e("TAG", "Unable to connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n\nLatitude and Longitude :\n" + result;
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n Unable to get Latitude and Longitude for this address location.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }


    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        String p1 = "";

        try {
            address = coder.getFromLocationName(strAddress, 5);
            assert address != null;

            if (address.size() > 0) {
                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                p1 = location.getLatitude() + ", " + location.getLongitude();  // new LatLng(( * 1E6), (location.getLongitude() * 1E6));
                Log.wtf("latlng", p1);

                return p1;
            } else {
                return "9.0820,8.6753";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return p1;
        }

    }

    public abstract static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        GestureDetector mGestureDetector;
        private OnItemClickListener mListener;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        protected interface OnItemClickListener {
            void onItemClick(View view, int position);
        }
    }
}
