package ssc.snow.app.gomyways.views.home.controler.menu.idverification

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_screen_id_verification.*
import kotlinx.android.synthetic.main.toolbar_child.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.IdVerificationViewModel
import java.io.File

class ScreenIdVerification : CommonActivity() {


    private lateinit var mViewModel: IdVerificationViewModel

    private var mImageUrl: String = ""
    private var mFlagIdUpload: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_id_verification)

        // initialisations
        init()
        onClicksOnViews()



    }


    private fun onClicksOnViews() {
        img_back.setOnClickListener {
            finish()

        }

        frame_photo_id_image.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {
                // Perform the action need to perform after permission has been done
                imageDialog()

            }

        }

        btn_submit_verification.setOnClickListener {

            // make flag true to show the dialog of success
            mFlagIdUpload = true

            // fire the api for the save user id
            saveuserIdentity()

        }

    }

    private fun init() {

        mViewModel = ViewModelProviders.of(this).get(IdVerificationViewModel::class.java)

        // set the center heading for the toolbar
        txt_center_heading.text = "ID Verification"

        if (isNetworkConnected) {
            mViewModel.getAllIds(sessionInstance.loggedInUserDetail.data!!.user!!.token)

        }
        mViewModel.liveIds().observe(this, Observer {

            if (it != null) {
                if (it.status) {


                    when (it.data!!.status) {
                        "0" -> {
                            img_check_uncheck.setImageResource(R.drawable.cross)
                        }
                        else -> {
                            img_check_uncheck.setImageResource(R.drawable.check)
                        }
                    }


                    // set the name to the fields
                    edt_id_fname.setText(it.data.first_name_id.toString())
                    edt_id_lname.setText(it.data.last_name_id.toString())

                    // load image of uploaded id
                    img_photo_id.setImageResource(0)
                    loadImageWithGlideBitmap(img_verified_or_not, it.data.identity_proof_url)

                    // show the dialog if uploaded new id for proof
                    if (mFlagIdUpload) {
                        successBox(it.message.toString())
                        // make it false for the next upload if prof
                        mFlagIdUpload = false
                    }


                } else {
                    showToast(it.message)
                }

            }
        })

    }



    // Call the api for the update profile
    private fun saveuserIdentity() {

        if (!isNetworkConnected) {
            showToast(resources.getString(R.string.provide_internet))
            return
        }

        // Parameter request body
        val mToken = RequestBody.create(MediaType.parse("text/plain"), sessionInstance.loggedInUserDetail.data!!.user!!.token)
        val mFname = RequestBody.create(MediaType.parse("text/plain"), edt_id_fname.text.toString())
        val mLname = RequestBody.create(MediaType.parse("text/plain"), edt_id_lname.text.toString())

        var mImageParts: MultipartBody.Part?

        // call api for the update profile
        mViewModel.run {
            // creating image format for upload

            if (!TextUtils.isEmpty(mImageUrl)) {
                // Pass it like this
                val file = File(mImageUrl)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                mImageParts = MultipartBody.Part.createFormData("idverify", file.name, requestFile)
            } else {

                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "")
                mImageParts = MultipartBody.Part.createFormData("idverify", "", requestFile)

            }

            // call api for the update profile
            saveuserIds(mToken,
                    mFname, mLname,
                    mImageParts as MultipartBody.Part)
        }

    }

    // Show the success box to confirm user that data is updated
    fun successBox(msg: String) {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(msg)
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                }
                .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == RESULT_OK) {
                val resultUri = result.uri

                //  File path
                mImageUrl = resultUri.path!!

               img_photo_id.setImageResource(0)
                img_photo_id.setImageURI(resultUri)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.wtf("mPath", result.error)
            }
        }
    }

    protected fun checkPermissions() {
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
                builder.setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(this@ScreenIdVerification,
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

    fun imageDialog() {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this@ScreenIdVerification)
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