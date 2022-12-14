package ssc.snow.app.gomyways.views.login

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_screen_register.*
import kotlinx.android.synthetic.main.vehiclelistview1.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.viewmodel.RegisterViewModel
import ssc.snow.app.gomyways.views.login.terms.TermsFragment
import java.io.File
import java.util.*

class ScreenRegister : CommonActivity() {

    private var mTermsAndCondition = ""


    private var mGender = ""
    private var mUserType = "1"

    private var mProfilePicPath = ""
    private var mIdProofPicPath = ""

    private var mProfileImageStatus = false


    private lateinit var mViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_register)


        // initialisations
        init()
        onClicks()
    }

    private fun onClicks() {


        txt_terms.setOnClickListener { onViewClicked(it) }
        checkbox_terms.setOnClickListener { onViewClicked(it) }
        signup_btn.setOnClickListener { onViewClicked(it) }
        txt_login.setOnClickListener { onViewClicked(it) }

        dob_et.setOnClickListener {
            calendarDialog()
        }

        frame_profile_image.setOnClickListener {
            mProfileImageStatus = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {
                // Perform the action need to perform after permission has been done
                imageDialog()
            }
        }

        frame_id_photo.setOnClickListener {
            mProfileImageStatus = false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {
                // Perform the action need to perform after permission has been done
                imageDialog()

            }


        }


    }

    private fun init() {

        // initialise View model
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        InjectorUtil.isNetworkConnected()

        val text = "I agree to the " + "<font color=#ff4f02>term's</font> & <font color=#ff4f02>privacy policy</font> of Gomywayride services Ltd."
        txt_terms!!.text = Html.fromHtml(text)

        // observe data
        mViewModel.liveRegisterRespone().observe(this, Observer {
            dismissIOSProgress()
            it?.let {

                if (it.status!!) {
                    sessionInstanceNotNull.isRememberMe = false
                    // after signup
                    sessionInstanceNotNull.isAfterSignup = true
                    sessionInstanceNotNull.afterSignupToken = it.data?.user!!.token

                    // set the remember credentials
                    sessionInstanceNotNull.emailRememberMe = email_et!!.text.toString().trim { it <= ' ' }
//                    sessionInstanceNotNull.pwdRememberMe = pass_et!!.text.toString().trim { it <= ' ' }
                    sessionInstanceNotNull.pwdRememberMe = pass_et_txt!!.text.toString().trim { it <= ' ' }
                    // Send the control to the verification screen

                    showToast(it.message)
                    goForNextScreen(ActivityLoggedInAfterSignup::class.java)

                } else
                    warningBox(getHtmlText(it.message))
            }
        })

    }

    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.checkbox_terms -> mTermsAndCondition = if (checkbox_terms!!.isChecked) "1" else ""

            R.id.signup_btn -> if (!checkForEmpty()) registeruser()

            R.id.txt_terms -> {
                val menuFragment = TermsFragment()
                menuFragment.show(supportFragmentManager, menuFragment.tag)
            }

            R.id.txt_login -> goForHomeFromLeftToRight(ScreenLogin::class.java)
        }
    }

    private fun checkForEmpty(): Boolean {
        var mFlag = false

        mGender = when {
            radio_male.isChecked -> "1"
            radio_female.isChecked -> "2"
            radio_other.isChecked -> "3"
            else -> ""
        }

        // Set the user type
        mUserType = if (switch1.isChecked) "0" else "1"

        when {

            fn_et.text.isBlank() -> {
                fn_et!!.error = resources.getString(R.string.field_cnt_empty)
                mFlag = true
            }
            ln_et.text.isBlank() -> {
                ln_et!!.error = resources.getString(R.string.field_cnt_empty)
                mFlag = true
            }
            username_et.text.isBlank() -> {
                username_et!!.error = resources.getString(R.string.field_cnt_empty)
                mFlag = true
            }
            dob_et.text.isBlank() -> {
                dob_et!!.error = resources.getString(R.string.field_cnt_empty)
                mFlag = true
            }
            email_et.text.isBlank() -> {
                email_et!!.error = resources.getString(R.string.field_cnt_empty)
                mFlag = true
            }
//            pass_et.text.isBlank() -> {
//                pass_et!!.error = resources.getString(R.string.field_cnt_empty)
//                mFlag = true
//            }
            pass_et_txt.text.toString().isBlank() -> {
                pass_et_txt!!.error = resources.getString(R.string.field_cnt_empty)
                mFlag = true
            }

            confirm_pass_et.text.toString().isBlank() -> {
                confirm_pass_et!!.error = "Please enter your password!"
                mFlag = true
            }

            pass_et_txt.text.toString() != confirm_pass_et.text.toString()->
            {
                confirm_pass_et!!.error= "Password mismatch re-enter your password!"

                mFlag=true
            }

            mProfilePicPath.isBlank() -> {
                showToast("Please provide profile photo")
                mFlag = true
            }
            mIdProofPicPath.isBlank() -> {
                showToast("Please provide Id proof photo")
                mFlag = true
            }
            mGender.isBlank() -> {
                showToast("Please provide gender")
                mFlag = true
            }
            mTermsAndCondition.isBlank() -> {
                showToast("Please accept the terms and policy of the app")
                mFlag = true
            }

        }

        return mFlag
    }

    // Call the api for the update profile
    private fun registeruser() {

        InjectorUtil.networkCheck()


        // Parameter request body
        val mTerms = RequestBody.create(MediaType.parse("text/plain"), mTermsAndCondition)
        val mFname = RequestBody.create(MediaType.parse("text/plain"), fn_et.text.toString().trim())
        val mLname = RequestBody.create(MediaType.parse("text/plain"), ln_et.text.toString().trim())
        val mUesername = RequestBody.create(MediaType.parse("text/plain"), username_et.text.toString().trim())
        val mDob = RequestBody.create(MediaType.parse("text/plain"), dob_et.text.toString())

        val mEmailId = RequestBody.create(MediaType.parse("text/plain"), email_et.text.toString().trim())
//        val mPassword = RequestBody.create(MediaType.parse("text/plain"), pass_et.text.toString().trim())
        val mPassword = RequestBody.create(MediaType.parse("text/plain"), pass_et_txt.text.toString().trim())
//        val mConfirmPassword = RequestBody.create(MediaType.parse("text/plain"), confirm_pass_et.text.toString().trim())

        val GN = RequestBody.create(MediaType.parse("text/plain"), mGender)
        val UT = RequestBody.create(MediaType.parse("text/plain"), mUserType)


        var mImageProfilePart: MultipartBody.Part? = null
        var mImageIdProofPart: MultipartBody.Part? = null

        // call api for the update profile
        mViewModel.run {
            // creating image format for upload
            if (mProfilePicPath.isNotEmpty()) {
                // Pass it like this
                val file = File(mProfilePicPath)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                mImageProfilePart = MultipartBody.Part.createFormData("profile_photo", file.name, requestFile) as MultipartBody.Part

            }

            if (mIdProofPicPath.isNotEmpty()) {

                // Pass it like this
                val file2 = File(mIdProofPicPath)
                val requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2)

                mImageIdProofPart = MultipartBody.Part.createFormData("id_photo", file2.name, requestFile2) as MultipartBody.Part

            }

            showIOSProgress()
            // call api for the update profile
            registerHIt(mTerms, mFname, mLname, mUesername, mDob, mEmailId, mPassword, GN, UT, mImageProfilePart!!, mImageIdProofPart!!)


        }

    }

    fun successBox(msg: String?) {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText(msg)
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                    goForHomeFromLeftToRight(ScreenLogin::class.java)
                }
                .show()
    }


    private fun calendarDialog() {

        //  Calendar cal = Calendar.getInstance();
        //  int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        var mCal = Calendar.getInstance()
        var mDayOfMonth = mCal.get(Calendar.DAY_OF_MONTH)
        var mMonth = mCal.get(Calendar.MONTH)
        var mYear = mCal.get(Calendar.YEAR)

        Log.wtf("cal", "$mDayOfMonth-$mMonth-$mYear")

        val dialog = DatePickerFragmentDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->

            //   Toast.makeText(applicationContext,
            //   "year $year month $monthOfYear day $dayOfMonth",
            //   Toast.LENGTH_SHORT).show()

            val mMonth = monthOfYear + 1
            dob_et.setText("$year-$mMonth-$dayOfMonth")

        }, mYear, mMonth, mDayOfMonth)


        /* Possible params */

        dialog.setMaxDate(System.currentTimeMillis())
        // dialog.setMinDate(System.currentTimeMillis())

        dialog.setCancelColor(Color.BLACK)
        dialog.setOkColor(Color.BLACK)
        dialog.accentColor = resources.getColor(R.color.colorPrimary)

        dialog.showYearPickerFirst(false)

        dialog.setCancelText("Cancel")
        dialog.setOkText("Choose")

        dialog.show(supportFragmentManager, "tag")


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == RESULT_OK) {
                val resultUri = result.uri

                if (mProfileImageStatus) {

                    //  File path
                    mProfilePicPath = resultUri.path!!

                    Glide.with(this)
                            .load(resultUri)
                            .into(img_profile)

                } else {

                    //  File path
                    mIdProofPicPath = resultUri.path!!

                    Glide.with(this)
                            .load(resultUri)
                            .into(img_id)

                    //  img_user_profile.setImageResource(0)
                    //    img_user_profile.setImageURI(resultUri)

                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.wtf("mPath", result.error)
            }
        }
    }

    private fun checkPermissions() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        + ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) !== PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // If we should give explanation of requested permissions
                // Show an alert dialog here with request explanation
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Camera, Read Storage and Write External" + " Storage permissions are required to do the task.")
                builder.setTitle("Please grant those permissions")
                builder.setPositiveButton("OK") { dialogInterface, i ->
                    ActivityCompat.requestPermissions(this@ScreenRegister,
                            arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_CODE)
                }

                builder.setNeutralButton("Cancel", null)
                val dialog = builder.create()
                dialog.show()

            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO),
                        MY_PERMISSIONS_REQUEST_CODE
                )
            }
        } else {

            // Todo: Do something, when permission are already granted
            imageDialog()
        }
    }

    private fun imageDialog() {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this@ScreenRegister)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CODE -> {
                // When request is cancelled, the results array are empty
                if (grantResults.size > 0 && grantResults[0] + grantResults[1] + grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                    //Todo: Do something after permission has been granted Permissions are granted
                    imageDialog()

                    Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show()
                } else {
                    // Permissions are denied
                    Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }

    }


}