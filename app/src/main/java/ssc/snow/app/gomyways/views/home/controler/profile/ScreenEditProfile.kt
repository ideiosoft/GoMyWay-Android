package ssc.snow.app.gomyways.views.home.controler.profile

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_screen_edit_profile.*
import kotlinx.android.synthetic.main.activity_screen_edit_profile.root_ll
import kotlinx.android.synthetic.main.layout_add_vehicle_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelLogin
import ssc.snow.app.gomyways.data.model.ModelStates
import ssc.snow.app.gomyways.data.model.ModelUserVehicles
import ssc.snow.app.gomyways.data.model.ModelVehicleAndTypes
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.viewmodel.ProfileViewModel
import ssc.snow.app.gomyways.views.home.adapter.AdapterAutoCompleteStates
import ssc.snow.app.gomyways.views.home.adapter.AdapterAutoCompleteVehicles
import ssc.snow.app.gomyways.views.home.adapter.AdapterAutoCompleteVehiclesTypes
import ssc.snow.app.gomyways.views.home.adapter.AdapterVehicles
import ssc.snow.app.gomyways.views.home.controler.profile.dialog.IDialogCallback
import ssc.snow.app.gomyways.views.home.controler.profile.dialog.VehicleListDialog
import java.io.File
import java.util.*


class ScreenEditProfile : CommonActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager

    //  Private lateinit var mListFeed: ArrayList<ModelFeed.Row?>
    private lateinit var mAdapterFeed: AdapterVehicles

    // View model decalarations
    private lateinit var mViewModel: ProfileViewModel

    private var mFlagDel: Boolean = false
    private var mFlagVehicle: Boolean = false
    private var mProfileImage: String = ""
    private var mVehicleImage: String = ""
    private var mVehicleImage2: String = ""
    private var mVehicleImage3: String = ""
    private var mVehicleImage4: String = ""
    private var mVehicleImage5: String = ""
    private var mVehicleImage6: String = ""

    // Array listing for the vehicle and types
    private var mListVehicles: ArrayList<ModelVehicleAndTypes.Data.Vehicle>? = null
    private var mListVehicleTypes: ArrayList<ModelVehicleAndTypes.Data.VehicleType>? = null

    private lateinit var mAdapterVehicleAutoComplete: AdapterAutoCompleteVehicles
    private lateinit var mAdapterVehicleTypeAutoComplete: AdapterAutoCompleteVehiclesTypes
    private lateinit var mAdapterStatesAutoComplete: AdapterAutoCompleteStates

    private var mVehicleId: String = ""
    private var mVehicleTypeId: String = ""

    private var mClickedImageView = ""

    private lateinit var mSearchDialog: VehicleListDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_edit_profile)

        // Set initial data
        initLIsting()
        initialise()

        sessionInstance.loggedInUserDetail.data!!.user?.let { updateUI(it) }

    }

    private fun initLIsting() {

        mListVehicles = arrayListOf()
        mListVehicleTypes = arrayListOf()

        img_back__.setOnClickListener {
            finish()


        }

        img_user_profile.setOnClickListener {

            mFlagVehicle = false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {
                // Perform the action need to perform after permission has been done
                imageDialog()

            }

        }

        img_camera.setOnClickListener {

            mFlagVehicle = false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {
                // Perform the action need to perform after permission has been done
                imageDialog()

            }

        }

        btn_update_profile.setOnClickListener {

            // show progress bar
            showIOSProgress()

            updateProfile()
        }

        edt_dob.setOnClickListener {
            //  uiHandlerMethod.getCalendarDialogDate(edt_dob)
            myDialog()
        }

        // listeners
        txt_add_vehicle.setOnClickListener { mView ->
            if (root_ll.isShown) {
                root_ll.visibility = View.GONE

                // Root_layout empty
                edt_vehicle_auto_complete.setText("")
                edt_vehicle_type_auto_complete.setText("")
                edt_vehicle_model.setText("")
                edt_plate_number.setText("")

                // IMAGE REMOVE from the view
                img_vehicle.setImageResource(0)


            } else {
                root_ll.visibility = View.VISIBLE
                scroll_view_profile.fullScroll(ScrollView.FOCUS_DOWN)
            }

        }

        frame_vehicle_image.setOnClickListener {
            mFlagVehicle = true
            mClickedImageView = "1"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {

                // Perform the action need to perform after permission has been done
                imageDialog()
            }
        }
        frame_vehicle_image2.setOnClickListener {
            mFlagVehicle = true
            mClickedImageView = "2"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {

                // Perform the action need to perform after permission has been done
                imageDialog()
            }
        }
        frame_vehicle_image3.setOnClickListener {
            mFlagVehicle = true
            mClickedImageView = "3"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {

                // Perform the action need to perform after permission has been done
                imageDialog()
            }
        }
        frame_vehicle_image4.setOnClickListener {
            mFlagVehicle = true
            mClickedImageView = "4"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {

                // Perform the action need to perform after permission has been done
                imageDialog()
            }
        }
        frame_vehicle_image5.setOnClickListener {
            mFlagVehicle = true
            mClickedImageView = "5"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {

                // Perform the action need to perform after permission has been done
                imageDialog()
            }
        }

        frame_vehicle_image6.setOnClickListener {

            mFlagVehicle = true
            mClickedImageView = "6"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions()
            } else {

                // Perform the action need to perform after permission has been done
                imageDialog()
            }
        }

        btn_submit_vehicle.setOnClickListener {

            mFlagDel = true   //  mFlag make true for the add vehicle to the list

            // fire the api for the add vehicle
            addVehicle()

        }
    }

    private fun initialise() {

        // set layout manager to the recycler view
        layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycle_vehicle.layoutManager = layoutManager

        // get update profile response
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        mViewModel.liveUpdateRespone().observe(this, Observer {

            // dismiss progress bar
            dismissIOSProgress()
            it?.let {
                if (it.status) {
                    sessionInstance.storeLoggedInUserDetail(it)
                    successBox(it.message.toString())
                } else
                    warningBox(it.message)
            }

        })

        // get user vehicles updated
        if (isNetworkConnected) {
            mViewModel.getUserVehicles(sessionInstance.loggedInUserDetail.data!!.user!!.token)

        }

        if (isNetworkConnected) {
            mViewModel.getVehiclesAndTypes(sessionInstance.loggedInUserDetail.data!!.user!!.token)
        }

        mViewModel.liveUserVehicles().observe(this, Observer {

            // dismiss progress bar
            dismissIOSProgress()

            it?.let {
                if (it.status) {

                    if (mFlagDel) {
                        showToast(it.message)
                        mFlagDel = false
                    }

                    //  For Add Vehicle Show Toast

                    // set recycler view
                    setRecyclerView(it, recycle_vehicle)
                } else {
                    warningBox(it.message)
                }
            }
        })

        //get response of live states
        mViewModel.liveStatesResponse().observe(this, Observer {
            it?.let {
                if (it.status!!) {

                    mAdapterStatesAutoComplete = AdapterAutoCompleteStates(this@ScreenEditProfile,
                            R.layout.layout_row_auto_complete, R.id.lbl_name, it.data!!)


                    edt_state_name.setAdapter(mAdapterStatesAutoComplete)

                    // Auto complete threshold
                    edt_state_name.threshold = 1

                    edt_state_name.setOnClickListener {
                        edt_state_name.showDropDown()
                    }

                    edt_state_name.setOnItemClickListener { parent, _, position, id ->

                        // This is the way to find selected object/item
                        val selectedType = parent.adapter.getItem(position) as ModelStates.Data

                        Log.wtf("mData", "$selectedType")
                        //Log.wtf("mData_", "${selectedType.id}")

                        // mVehicleTypeId = "${selectedType.id}"
                        edt_state_name.setText(selectedType.name)

                    }


                }
            }

        })

        // get response of vehicle and types together
        mViewModel.liveVehiclesAndTypes().observe(this, Observer {
            dismissIOSProgress()

            if (it != null) {
                if (it.status) {
                    // set the listing array
                    mListVehicles!!.addAll(it.data!!.vehicles)           // (it.data!!.vehicles)
                    mListVehicleTypes!!.addAll(it.data.vehicle_types)

                    Log.wtf("size-->", "${mListVehicles!!.size}")


                    // Initialize a new array adapter object
                    //  mAdapterVehicleAutoComplete = AdapterAutoCompleteVehicles(this@ScreenEditProfile,
                    //    R.layout.layout_row_auto_complete, R.id.lbl_name, mListVehicles!!)

                    mAdapterVehicleTypeAutoComplete = AdapterAutoCompleteVehiclesTypes(this@ScreenEditProfile,
                            R.layout.layout_row_auto_complete, R.id.lbl_name, mListVehicleTypes!!)


                    // edt_vehicle_auto_complete.setAdapter(mAdapterVehicleAutoComplete)
                    edt_vehicle_type_auto_complete.setAdapter(mAdapterVehicleTypeAutoComplete)

                    // Auto complete threshold

                    // The minimum number of characters to type to show the drop down
                    //  edt_vehicle_auto_complete.threshold = 1
                    edt_vehicle_type_auto_complete.threshold = 1

//                    edt_vehicle_auto_complete.setOnItemClickListener { parent, view, position, id ->
//                        //this is the way to find selected object/item
//                        val selectedVehicle = parent.adapter.getItem(position) as ModelVehicleAndTypes.Data.Vehicle
//
//                        Log.wtf("mData", "$selectedVehicle")
//                        Log.wtf("mData_", "${selectedVehicle.id}")
//
//                        mVehicleId = "${selectedVehicle.id}"
//                    }

                    edt_vehicle_auto_complete.setOnClickListener {
                        VehicleListDialog(this@ScreenEditProfile, mListVehicles, mDialogCallback).show()
                    }

                    edt_vehicle_type_auto_complete.setOnItemClickListener { parent, _, position, id ->

                        // This is the way to find selected object/item
                        val selectedType = parent.adapter.getItem(position) as ModelVehicleAndTypes.Data.VehicleType

                        Log.wtf("mData", "$selectedType")
                        Log.wtf("mData_", "${selectedType.id}")

                        mVehicleTypeId = "${selectedType.id}"

                    }


                    // Set a focus change listener for auto complete text view
//                    edt_vehicle_auto_complete.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
//                        if (b) {
//                            // Display the suggestion dropdown on focus
//                            edt_vehicle_auto_complete.showDropDown()
//                        }
//                    }

                    edt_vehicle_type_auto_complete.setOnClickListener {
                        edt_vehicle_type_auto_complete.showDropDown()
                    }
//                    edt_vehicle_type_auto_complete.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
//                        if (b) {
//                            // Display the suggestion dropdown on focus
//                            edt_vehicle_type_auto_complete.showDropDown()
//                        }
//                    }

                }
            }
        })

    }

    private val mDialogCallback = object : IDialogCallback {
        override fun getVehicle(nVName: String) {

            mListVehicles!!.forEach { mItem ->

                if (mItem.vehicle_name?.toLowerCase().equals(nVName.toLowerCase(), true)) {
                    mVehicleId = mItem.id.toString()

                    edt_vehicle_auto_complete.setText(nVName)
                }
            }
        }
    }

    private fun updateUI(user: ModelLogin.Data.User) {
        user.apply {
            // Set the values to the variable
            edt_first_name.setText(first_name)
            edt_last_name.setText(last_name)
            edt_dob.setText(date_of_birth)
            edt_about.setText(about_user)
            edt_phn_number.setText(mobile)


            // set address
            edt_hno.setText(house_number)
            edt_state_name.setText(state_name)
            edt_cityname.setText(city_name)
            edt_streetname.setText(street_name)


            // edt_paystack_.setText(paystack_secret)

            // set gender from backend
            when (gender) {
                "1" -> {
                    radio_male.isChecked = true
                }
                "2" -> {
                    radio_female.isChecked = true
                }
                else -> {
                    radio_other.isChecked = true
                }

            }

            // Driver or not 1 - for driver ,0- passenger
            when (user_type) {
                "0" -> {
                    switch1.isChecked = true
                }
                else -> {
                    switch1.isChecked = false
                }
            }

            // load user user profile image
            // http://gomywayride.com/uploads/users/1576750709-2891-cropped4528647941257985304.jpg
            // img_user_profile.setImageResource(0)
            loadImageWithGlideBitmap(img_user_profile, profile_image)
            //    "http://gomywayride.com/uploads/users/1576750709-2891-cropped4528647941257985304.jpg".trim())
//             Glide.with(this@ScreenEditProfile)
//                    .asBitmap()
//                    .load("http://gomywayride.com/uploads/users/1576750709-2891-cropped4528647941257985304.jpg")
//                    .into(img_user_profile)
        }
    }

    // Set recycler view for the upcoming trips
    private fun setRecyclerView(mModel: ModelUserVehicles, mRecycleSearch: RecyclerView) {

        mModel.data?.userVehicles?.let {
            mAdapterFeed = AdapterVehicles(applicationContext, it, mCallback)
            // Set adapter on the recyclerview
            mRecycleSearch.adapter = mAdapterFeed
        }


    }

    val mCallback = object : IProfileCallback {
        override fun delUserVehicle(mVehicleId: String) {

            if (!isNetworkConnected) {
                showToast(resources.getString(R.string.provide_internet))
                return
            }
            confirmDelete("Do you really want to delete?", mVehicleId)
        }
    }

    fun confirmDelete(msg: String?, mVehicleId: String) {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Confirmation")
                .setContentText(Html.fromHtml(msg).toString())
                .setConfirmButton("Confirm") { sDialog ->
                    sDialog.dismissWithAnimation()

                    if (!isNetworkConnected) {
                        showToast(resources.getString(R.string.provide_internet))

                    } else {

                        // Show progress when firing api
                        mFlagDel = true
                        mViewModel.delUserVehicle(sessionInstance.loggedInUserDetail.data!!.user!!.token, mVehicleId)


                    }
                }
                .setCancelButton("Cancel") { sDialog ->
                    sDialog.dismissWithAnimation()
                }
                .show()
    }

    // Call the api for the update profile
    private fun updateProfile() {

        if (!isNetworkConnected) {
            showToast(resources.getString(R.string.provide_internet))
            return
        }

        // Parameter request body
        val mToken = RequestBody.create(MediaType.parse("text/plain"), sessionInstance.loggedInUserDetail.data!!.user!!.token)
        val mFname = RequestBody.create(MediaType.parse("text/plain"), edt_first_name.text.toString())
        val mLname = RequestBody.create(MediaType.parse("text/plain"), edt_last_name.text.toString())
        val mDob = RequestBody.create(MediaType.parse("text/plain"), edt_dob.text.toString())
        val mPhoneNo = RequestBody.create(MediaType.parse("text/plain"), edt_phn_number.text.toString())
        val mAbout = RequestBody.create(MediaType.parse("text/plain"), edt_about.text.toString())
        // val mPayStack = RequestBody.create(MediaType.parse("text/plain"), edt_paystack_.text.toString())
        val mPayStack = RequestBody.create(MediaType.parse("text/plain"), "")


        val mHNO = RequestBody.create(MediaType.parse("text/plain"), edt_hno.text.toString())
        val mStrN = RequestBody.create(MediaType.parse("text/plain"), edt_streetname.text.toString())
        val mCITYN = RequestBody.create(MediaType.parse("text/plain"), edt_cityname.text.toString())
        val mSTATENAME = RequestBody.create(MediaType.parse("text/plain"), edt_state_name.text.toString())

        // Get the gender
        val gender = when {
            radio_male.isChecked -> "1"
            radio_female.isChecked -> "2"
            else -> "3"
        }

        // Set the user type
        val userType = if (switch1.isChecked) "0" else "1"

        val mGender = RequestBody.create(MediaType.parse("text/plain"), gender)
        val mUserType = RequestBody.create(MediaType.parse("text/plain"), userType)

        var mImageParts: MultipartBody.Part? = null

        // call api for the update profile
        mViewModel.run {
            // creating image format for upload

            mImageParts = if (!TextUtils.isEmpty(mProfileImage)) {
                // Pass it like this
                val file = File(mProfileImage)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                MultipartBody.Part.createFormData("profile_image", file.name, requestFile)
            } else {

                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "")
                MultipartBody.Part.createFormData("profile_image", "", requestFile)

            }

            // call api for the update profile
            updateProfile(
                    mHNO, mStrN, mCITYN, mSTATENAME,
                    mToken,
                    mFname, mLname,
                    mPhoneNo, mDob,
                    mAbout, mGender,
                    mPayStack, mUserType,
                    mImageParts as MultipartBody.Part)
        }

    }

    // Show the success box to confirm user that data is updated
    fun successBox(msg: String) {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
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

                if (mFlagVehicle) {
                    when (mClickedImageView) {
                        "1" -> {
                            //  File path
                            mVehicleImage = resultUri.path!!

                            //img_vehicle.setImageResource(0)
                            img_vehicle.setImageURI(resultUri)
                        }
                        "2" -> {
                            //  File path
                            mVehicleImage2 = resultUri.path!!
                            //img_vehicle.setImageResource(0)
                            img_vehicle2.setImageURI(resultUri)
                        }
                        "3" -> {
                            //  File path
                            mVehicleImage3 = resultUri.path!!
                            //img_vehicle.setImageResource(0)
                            img_vehicle3.setImageURI(resultUri)
                        }
                        "4" -> {
                            //  File path
                            mVehicleImage4 = resultUri.path!!
                            //img_vehicle.setImageResource(0)
                            img_vehicle4.setImageURI(resultUri)
                        }
                        "5" -> {
                            //  File path
                            mVehicleImage5 = resultUri.path!!
                            //img_vehicle.setImageResource(0)
                            img_vehicle5.setImageURI(resultUri)
                        }
                        "6" -> {
                            //  File path
                            mVehicleImage6 = resultUri.path!!
                            //img_vehicle.setImageResource(0)
                            img_vehicle6.setImageURI(resultUri)
                        }

                    }


                } else {

                    //  File path
                    mProfileImage = resultUri.path!!

                    Glide.with(this)
                            .load(resultUri)
                            .into(img_user_profile)

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
                    ActivityCompat.requestPermissions(this@ScreenEditProfile,
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
                .start(this@ScreenEditProfile)
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

    // Call the api for the update profile
    private fun addVehicle() {

        if (!isNetworkConnected) {
            showToast(resources.getString(R.string.provide_internet))
            return
        }


        // Parameter request body
        val mToken = RequestBody.create(MediaType.parse("text/plain"), sessionInstance.loggedInUserDetail.data!!.user!!.token)
        val mVId = RequestBody.create(MediaType.parse("text/plain"), mVehicleId)
        val mVType = RequestBody.create(MediaType.parse("text/plain"), mVehicleTypeId)
        val mVModel = RequestBody.create(MediaType.parse("text/plain"), edt_vehicle_model.text.toString())
        val mVPlateNo = RequestBody.create(MediaType.parse("text/plain"), edt_plate_number.text.toString())

        var mImageParts: ArrayList<MultipartBody.Part> = arrayListOf()

        //  Call api for the update profile
        mViewModel.run {

            if (mVehicleImage.isEmpty() && mVehicleImage2.isEmpty() && mVehicleImage3.isEmpty() && mVehicleImage4.isEmpty() && mVehicleImage5.isEmpty()) {
                showToast("Minimum 1 photos required")
                return
            }

            // Show progress while uploading 5 photos
            showIOSProgress()



            if (mVehicleImage.isNotEmpty()) {
                // Pass it like this
                val file = File(mVehicleImage)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)

                mImageParts.add(MultipartBody.Part.createFormData("userfile[]", file.name, requestFile))
            }
            if (mVehicleImage2.isNotEmpty()) {

                // Pass it like this
                val file2 = File(mVehicleImage2)
                val requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2)

                mImageParts.add(MultipartBody.Part.createFormData("userfile[]", file2.name, requestFile2))

            }
            if (mVehicleImage3.isNotEmpty()) {

                // Pass it like this
                val file3 = File(mVehicleImage3)
                val requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3)

                mImageParts.add(MultipartBody.Part.createFormData("userfile[]", file3.name, requestFile3))

            }
            if (mVehicleImage4.isNotEmpty()) {

                // Pass it like this
                val file4 = File(mVehicleImage4)
                val requestFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), file4)

                mImageParts.add(MultipartBody.Part.createFormData("userfile[]", file4.name, requestFile4))

            }
            if (mVehicleImage5.isNotEmpty()) {

                // Pass it like this
                val file5 = File(mVehicleImage5)
                val requestFile5 = RequestBody.create(MediaType.parse("multipart/form-data"), file5)
                mImageParts.add(MultipartBody.Part.createFormData("userfile[]", file5.name, requestFile5))

            }

            // Creating image format for upload
            if (mVehicleImage6.isNotEmpty()) {

                // Pass it like this
                val file6 = File(mVehicleImage6)
                val requestFile6 = RequestBody.create(MediaType.parse("multipart/form-data"), file6)

                mImageParts.add(MultipartBody.Part.createFormData("userfile[]", file6.name, requestFile6))

            }

            // C
            // all api for the update profile
            addUserVehicle(mToken, mVId, mVType, mVModel, mVPlateNo, mImageParts)
        }

    }

    fun myDialog() {

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
            edt_dob.setText("$year-$mMonth-$dayOfMonth")

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

    override fun onStart() {
        super.onStart()
        dismissIOSProgress()
    }

}




