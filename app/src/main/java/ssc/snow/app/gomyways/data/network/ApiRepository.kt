package ssc.snow.app.gomyways.data.network


import android.os.Handler
import android.os.Message
import android.util.Log
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ssc.snow.app.gomyways.data.model.ModelCommon
import ssc.snow.app.gomyways.data.model.ModelLogin
import ssc.snow.app.gomyways.data.model.ModelSearchRideResult
import ssc.snow.app.gomyways.data.network.google_network.ApiInterfaceGoogle
import ssc.snow.app.gomyways.data.network.google_network.ApiUtilsGoogle


class ApiRepository {

    private val apiInterface: ApiInterface
    private val apiInterfacefroGoogle: ApiInterfaceGoogle

    init {
        apiInterface = ApiUtils.getApiServices()
        apiInterfacefroGoogle = ApiUtilsGoogle.getGoogleServices()
    }

    // ##############
    // Api's call
    // ##############
//    fun register(mTerms: String, username: String, mEmail: String, mPwd: String, mHandler: Handler) {
//
//        val msg = Message()
//        val call = apiInterface.registerApi(mTerms, username, mEmail, mPwd, mPwd)
//        call.enqueue(object : Callback<ModelAfterSignup> {
//            override fun onResponse(call: Call<ModelAfterSignup>, response: Response<ModelAfterSignup>) {
//
//                if (response.body() != null) {
//                    if (response.body()!!.status!!) {
//
//                        Log.wtf("+++++++", "+++ ApiRepository register success responsebody +++" + response.body()!!.status)
//                        Log.wtf("+++++++", "+++ ApiRepository register success responsebody +++" + response.body()!!)
//
//                        msg.what = ApiInterface.REGISTER_SUCCESS
//                        msg.obj = response.body()
//                        mHandler.sendMessage(msg)
//
//                    } else {
//
//                        Log.wtf("+++++++", "+++ ApiRepository register failure responsebody +++" + response.body()!!.status)
//
//                        msg.what = ApiInterface.REGISTER_SUCCESS
//                        msg.obj = response.body()
//                        mHandler.sendMessage(msg)
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ModelAfterSignup>, t: Throwable) {
//
//                Log.d("+++++++", "+++ ApiRepository register onFailure responsebody +++" + t.message)
//                msg.what = ApiInterface.REGISTER_FAILURE
//                msg.obj = t.message
//                mHandler.sendMessage(msg)
//
//            }
//        })
//    }

    suspend fun register(terms_conditions: RequestBody, mFname: RequestBody, mLname: RequestBody, username: RequestBody,
                         mDOB: RequestBody, mEmail: RequestBody, mPass: RequestBody, mGender: RequestBody,
                         userType: RequestBody, profile_photo: MultipartBody.Part, id_photo: MultipartBody.Part) =
            apiInterface.register(
                    terms_conditions,
                    mFname, mLname, username,
                    mDOB, mEmail, mPass, mPass, mGender, userType, profile_photo, id_photo)


    suspend fun socialLogin(mDeviceToken: String, mType: String, mSocialId: String, mEmail: String) =
            apiInterface.socialLoginApi(mDeviceToken, mType, mSocialId, mEmail)


    fun login(mToken: String, mEmail: String, mPwd: String, mHandler: Handler) {
        val msg = Message()

        val call = apiInterface.loginApi(mToken, mEmail, mPwd)

        call.enqueue(object : Callback<ModelLogin> {

            override fun onResponse(call: Call<ModelLogin>, response: Response<ModelLogin>) {

                if (response.body() != null) {
                    if (response.body()!!.status) {

                        Log.wtf("+++++++", "+++ ApiRepository register success responsebody +++" + response.body()!!.status)
                        Log.wtf("+++++++", "+++ ApiRepository register success responsebody +++" + response.body()!!)
                        Log.wtf("+++++++", "+++ ApiRepository register success responsemessage +++" + response.body()!!.message)
                        msg.what = ApiInterface.LOGIN_SUCCESS
                        msg.obj = response.body()
                        mHandler.sendMessage(msg)

                    } else {

                        Log.wtf("+++++++", "+++ ApiRepository register failure responsebody +++" + response.body()!!.status)
                        Log.wtf("+++++++", "+++ ApiRepository register failure responsemessage +++" + response.body()!!.message)
                        msg.what = ApiInterface.LOGIN_SUCCESS
                        msg.obj = response.body()
                        mHandler.sendMessage(msg)

                    }
                }
            }

            override fun onFailure(call: Call<ModelLogin>, t: Throwable) {

                Log.d("+++++++", "+++ ApiRepository register onFailure responsebody +++" + t.message)
                msg.what = ApiInterface.LOGIN_FAILURE
                msg.obj = t.message
                mHandler.sendMessage(msg)

            }
        })
    }

    suspend fun getEmailStatus(mToken: String) =
            apiInterface.getEmailStatus(mToken)


    suspend fun getTermsAndConditions() =
            apiInterface.getTermsAndConditions()


    fun logout(mLoggedInUserID: String, mHandler: Handler) {

        val msg = Message()
        val call = apiInterface.logoutApi(mLoggedInUserID)

        call.enqueue(object : Callback<ModelCommon> {

            override fun onResponse(call: Call<ModelCommon>, response: Response<ModelCommon>) {

                if (response.body() != null) {
                    if (response.body()!!.status) {

                        Log.wtf("+++++++", "+++ ApiRepository logout success responsebody +++" + response.body()!!.status)
                        Log.wtf("+++++++", "+++ ApiRepository logout success responsebody +++" + response.body()!!)
                        Log.wtf("+++++++", "+++ ApiRepository logout success responsemessage +++" + response.body()!!.message!!)
                        msg.what = ApiInterface.LOGOUT_SUCCESS
                        msg.obj = response.body()
                        mHandler.sendMessage(msg)

                    } else {

                        Log.wtf("+++++++", "+++ ApiRepository logout failure responsebody +++" + response.body()!!.status)
                        Log.wtf("+++++++", "+++ ApiRepository logout failure responsemessage +++" + response.body()!!.message!!)
                        msg.what = ApiInterface.LOGOUT_SUCCESS
                        msg.obj = response.body()
                        mHandler.sendMessage(msg)

                    }
                }
            }

            override fun onFailure(call: Call<ModelCommon>, t: Throwable) {

                Log.d("+++++++", "+++ ApiRepository register logout responsebody +++" + t.message)
                msg.what = ApiInterface.LOGOUT_FAILURE
                msg.obj = t.message
                mHandler.sendMessage(msg)

            }
        })
    }


    suspend fun getRecentSearches(mToken: String) =
            apiInterface.recentSearches(mToken)


    fun searchRide(token: String, mFrom: String, mTo: String, mLeavingDate: String, mHandler: Handler) {

        val msg = Message()
        val call = apiInterface.searchRide(token, mFrom, mTo, mLeavingDate)

        call.enqueue(object : Callback<ModelSearchRideResult> {
            override fun onResponse(call: Call<ModelSearchRideResult>, response: Response<ModelSearchRideResult>) {

                if (response.body() != null) {
                    if (response.body()!!.status!!) {

                        Log.wtf("+++++++", "+++ ApiRepository searchTrip success responsebody +++" + response.body()!!.status)
                        Log.wtf("+++++++", "+++ ApiRepository searchTrip success responsebody +++" + response.body()!!)
                        Log.wtf("+++++++", "+++ ApiRepository searchTrip success responsemessage +++" + response.body()!!.message!!)
                        msg.what = ApiInterface._SUCCESS
                        msg.obj = response.body()
                        mHandler.sendMessage(msg)

                    } else {

                        Log.wtf("+++++++", "+++ ApiRepository searchTrip failure responsebody +++" + response.body()!!.status)
                        Log.wtf("+++++++", "+++ ApiRepository searchTrip failure responsemessage +++" + response.body()!!.message!!)
                        msg.what = ApiInterface._SUCCESS
                        msg.obj = response.body()
                        mHandler.sendMessage(msg)
                    }
                }
            }

            override fun onFailure(call: Call<ModelSearchRideResult>, t: Throwable) {

                Log.d("+++++++", "+++ ApiRepository searchTrip onFailure responsebody +++" + t.message)
                msg.what = ApiInterface._FAILURE
                msg.obj = t.message
                mHandler.sendMessage(msg)

            }
        })
    }

    suspend fun forgotPass(mEmail: String) =
            apiInterface.forgotPassword(mEmail)


    suspend fun phoneVerification(phone: String, mToken: String) =
            apiInterface.hitPhoneVerification(phone, mToken)

    suspend fun confirmCode(mConfirmCode: String, mToken: String) =
            apiInterface.confirmCode(mConfirmCode, mToken)


    suspend fun resendEmail(mToken: String) =
            apiInterface.emailVerifyAfterSignup(mToken)


    suspend fun loginAfterSignup(mToken: String, mEmail: String, mPwd: String) =
            apiInterface.loginAfterSignupApi(mToken, mEmail, mPwd)


    suspend fun updateProfile(mHno: RequestBody, mStreet: RequestBody, mCityName: RequestBody, mStateName: RequestBody, mToken: RequestBody, mFname: RequestBody, mLname: RequestBody, mPhoneNo: RequestBody, mDOB: RequestBody, mAbout: RequestBody, mGender: RequestBody,
                              mPayStack: RequestBody, userType: RequestBody, image: MultipartBody.Part) =
            apiInterface.updateProfile(mHno, mStreet, mCityName, mStateName, mToken, mFname, mLname, mPhoneNo, mDOB, mAbout, mGender, mPayStack, userType, image)

    suspend fun getStates(mToken: String) = apiInterface.getStates(mToken)

    suspend fun getUserVehicles(mToken: String) = apiInterface.getUserVehicle(mToken)

    suspend fun delUserVehicle(mToken: String, mVehicleId: String) = apiInterface.deleteVehicle(mToken, mVehicleId)

    suspend fun getVehiclesAndTypes(mToken: String) = apiInterface.getVehiclesAndType(mToken)

    suspend fun addUserVehicle(mToken: RequestBody, vehicle_name: RequestBody, vehicleType: RequestBody, mModel: RequestBody,
                               mPlateNo: RequestBody, image: ArrayList<MultipartBody.Part>) = apiInterface
            .addUserVehicle(mToken, vehicle_name, vehicleType, mModel, mPlateNo, image)

    /***
     * *
     * email Verifications and all Operations module
     * ***/
    suspend fun getAllAssociat_emails(mToken: String) =
            apiInterface.getAllEmails(mToken)

    suspend fun addUser_email(mToken: String, mEmails: String) =
            apiInterface.saveUserEmail(mToken, mEmails)

    suspend fun deleteAndMakePrimaryUserEmail(mToken: String, mEmails: String, mType: String) =
            apiInterface.deleteEmail(mToken, mEmails, mType)

    suspend fun resendVerification_email(mToken: String, mEmails: String, mType: String, mEmailType: String) =
            apiInterface.resendVerificationEmail(mToken, mEmails, mType, mEmailType)

    /****
     * Id verifications
     * ***/
    suspend fun saveUerIdentity(mToken: RequestBody, mFirstname: RequestBody, mLastname: RequestBody, image: MultipartBody.Part) =
            apiInterface.saveUserIdentity(mToken, mFirstname, mLastname, image)

    suspend fun getVerifedIds(mToken: String) =
            apiInterface.getVerifiedIds(mToken)

    suspend fun getNotificaionSettings(mToken: String) =
            apiInterface.getNotificationSettings(mToken)

    suspend fun saveNotificationSettings(mToken: String, mSMS: String, mEmail: String, mPush: String) =
            apiInterface.saveNotificationSettings(mToken, mSMS, mEmail, mPush)

    /***
     * payouts
     * ***/
    suspend fun getPayoutList(mToken: String) =
            apiInterface.getPayouts(mToken)

    /* ***
     * Bank details
     * ***/
    suspend fun getAllSavedCards(mToken: String) =
            apiInterface.getBankDetail(mToken)

    suspend fun getBankNames(mToken: String) =
            apiInterface.getBankNames(mToken)

    suspend fun addBankCard(mToken: String, acc_no: String, bank_user_name: String, bank_name: String) =
            apiInterface.saveBankAccount(mToken, acc_no, bank_user_name, bank_name)


    suspend fun deleteBankCard(mToken: String, mId: String) =
            apiInterface.delBankCard(mToken, mId)

    /* ***
     * About us
     * ***/
    suspend fun getAboutUs(mToken: String) =
            apiInterface.getAboutUs(mToken)

    /* ***
     * Help content
     * ***/
    suspend fun getHelpContent(mToken: String) =
            apiInterface.getHelpContent(mToken)

    /* ***
     * Promoition apis
     * ***/
    suspend fun getRewards(mToken: String) =
            apiInterface.getRewards(mToken)

    suspend fun getOffers(mToken: String) =
            apiInterface.getOffers(mToken)

    suspend fun getReferCode(mToken: String) =
            apiInterface.getReferCode(mToken)

    suspend fun getAllNotifications(mToken: String) =
            apiInterface.getAllNotifications(mToken)

    suspend fun getPostTripStatus(mToken: String) =
            apiInterface.getPostTripStatus(mToken)


    suspend fun getMyTrips(mToken: String, mType: String) =
            apiInterface.MyTrips(mToken, mType)

    suspend fun receiveRequests(mToken: String) =
            apiInterface.receiveRequests(mToken)

    suspend fun viewTripDetail(mToken: String, mTripId: String) =
            apiInterface.viewPostTrip(mToken, mTripId)

    suspend fun closeOpenTrip(mToken: String, mTripId: String, mStatus: String) =
            apiInterface.closeTrip(mToken, mTripId, mStatus)

    suspend fun cancelTrip(mToken: String, mTripId: String, mStatus: String) =
            apiInterface.cancelTrip(mToken, mTripId, mStatus)

    suspend fun markAsComplete(mToken: String, mTripId: String, mStatus: String) =
            apiInterface.markAsComplete(mToken, mTripId, mStatus)

    /* ***
       *  My Rides module
       * ***/
    suspend fun getMyRides(mToken: String, mType: String) =
            apiInterface.myRides(mToken, mType)

    suspend fun viewRideDetail(mToken: String, mRequestId: String) =
            apiInterface.viewRideDetail(mToken, mRequestId)

    suspend fun cancelRide(mToken: String, mRequestId: String, mStatus: String) =
            apiInterface.cancelRideByPassenger(mToken, mRequestId, mStatus)

    suspend fun submitReview(mToken: String, mRequestId: String, mRating: String, posted_trip_id: String,
                             mPosted_trip_stops_id: String, mRequested_trip_id: String,
                             mPassengerId: String, mDriver_id: String, mComment: String) =
            apiInterface.submitReviewByPassenger(mToken, mRequestId, mRating, posted_trip_id,
                    mPosted_trip_stops_id, mRequested_trip_id, mPassengerId, mDriver_id, "0", mComment)

    /* ***
     *  Inbox module
     * ***/
    suspend fun getInbox(mToken: String) =
            apiInterface.getInbox(mToken)

    suspend fun getAllChat(mToken: String, mConverdationId: String) =
            apiInterface.getAllChat(mToken, mConverdationId)

    suspend fun sendMessage(mToken: String, mMsg: String, mfromId: String, mToId: String, mConverdationId: String) =
            apiInterface.sendMessage(mToken, mMsg, mfromId, mToId, mConverdationId)

    suspend fun getRecentMessage(mToken: String, mConverdationId: String) =
            apiInterface.getRecentMessages(mToken, mConverdationId)


}