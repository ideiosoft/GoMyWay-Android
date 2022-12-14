package ssc.snow.app.gomyways.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import ssc.snow.app.gomyways.data.model.*
import ssc.snow.app.gomyways.data.model.inbox.ModelAllChat
import ssc.snow.app.gomyways.data.model.inbox.ModelInboxList
import ssc.snow.app.gomyways.data.model.inbox.ModelSendMessage

interface ApiInterface {

    companion object {

        val _SUCCESS = 1
        val _FAILURE = 2

        val REGISTER_SUCCESS = 3
        val REGISTER_FAILURE = 4

        val LOGIN_SUCCESS = 5
        val LOGIN_FAILURE = 6

        val LOGOUT_SUCCESS = 7
        val LOGOUT_FAILURE = 8

    }

//    @FormUrlEncoded
//    @POST("authenticate/register")
//    fun registerApi(
//              @Field("terms_and_conditions") mTerms: String,
//               @Field("username") username: String,
//               @Field("email") email: String,
//               @Field("password") password: String,
//               @Field("confirm_password") confirm_password: String): Call<ModelAfterSignup>
//    https://gomywayride.com/api/authenticate/register

    @Multipart
    @POST("authenticate/register")
    suspend fun register(
            @Part("terms_and_conditions") terms_and_conditions: RequestBody,
            @Part("first_name") first_name: RequestBody,
            @Part("last_name") last_name: RequestBody,
            @Part("username") username: RequestBody,
            @Part("date_of_birth") date_of_birth: RequestBody,
            @Part("email") email: RequestBody,
            @Part("password") password: RequestBody,
            @Part("confirm_password") confirm_password: RequestBody,
            @Part("gender") gender: RequestBody,  // (1 = Male, 2 = Female,3 = Other)
            @Part("user_type") user_type: RequestBody,  // (0 = driver, 1 = passenger)
            @Part profile_photo: MultipartBody.Part,
            @Part id_photo: MultipartBody.Part): ModelAfterSignup

    
    @FormUrlEncoded
    @POST("authenticate/login")
    fun loginApi(
            @Field("device_token") device_token: String,
            @Field("username") username: String,
            @Field("password") password: String): Call<ModelLogin>


    @FormUrlEncoded
    @POST("Apidata/email-status")
    suspend fun getEmailStatus(
            @Field("token") token: String): ModelLogin


    @FormUrlEncoded
    @POST("Apidata/social-login")
    suspend fun socialLoginApi(
            @Field("device_token") device_token: String,
            @Field("type") type: String,
            @Field("social_id") social_id: String,
            @Field("email") email: String,
            @Field("first_name") first_name: String = "",
            @Field("last_name") last_name: String = "",
            @Field("gender") gender: String = ""
    ): ModelLogin


    @FormUrlEncoded
    @POST("Apidata/get-terms")
    suspend fun getTermsAndConditions(
            @Field("dummy") dummy: String = ""): ModelTerms


    @FormUrlEncoded
    @POST("authenticate/logout")
    fun logoutApi(
            @Field("id") id: String): Call<ModelCommon>

    @FormUrlEncoded
    @POST("Apidata/forgot-password")
    suspend fun forgotPassword(
            @Field("email") email: String): ModelCommon

    /****
     * Phone number verification
     * ****/
    @FormUrlEncoded
    @POST("Apidata/verify-mobile-code")
    suspend fun hitPhoneVerification(
            @Field("mobile") phone: String,
            @Field("token") token: String): ModelCommon

    @FormUrlEncoded
    @POST("Apidata/confirm-code")
    suspend fun confirmCode(
            @Field("code") code: String,
            @Field("token") token: String): ModelCommon


    @FormUrlEncoded
    @POST("Apidata/resend-verification")
    suspend fun emailVerifyAfterSignup(
            @Field("token") token: String): ModelCommon

    @FormUrlEncoded
    @POST("authenticate/login")
    suspend fun loginAfterSignupApi(
            @Field("device_token") device_token: String,
            @Field("username") username: String,
            @Field("password") password: String
    ): ModelLogin


    /****
     * Passenger Modules
     * ****/
    @FormUrlEncoded
    @POST("Apidata/get-recent-search")
    suspend fun recentSearches(
            @Field("token") token: String): ModelRecentSearches


    @FormUrlEncoded
    @POST("Apidata/search-ride")
    fun searchRide(
            @Field("token") token: String,
            @Field("fromTrip") from: String,
            @Field("toTrip") destination: String,
            @Field("leaving") leaving_date: String): Call<ModelSearchRideResult>


    @FormUrlEncoded
    @POST("Apidata/search-trip-detail")
    fun searchedTripDetailsApi(
            @Field("trip_id") trip_id: String): Call<ModelSearchedTripDetail>

    @FormUrlEncoded
    @POST("Apidata/user-detail")
    fun userDetails(
            @Field("token") token: String): Call<ModelUserDetail>

    @Multipart
    @POST("Apidata/update-user-profile")
    suspend fun updateProfile(
            @Part("house_number") house_number: RequestBody,
            @Part("street_name") street_name: RequestBody,
            @Part("city_name") city_name: RequestBody,
            @Part("state_name") state_name: RequestBody,
            @Part("token") token: RequestBody,
            @Part("first_name") first_name: RequestBody,
            @Part("last_name") last_name: RequestBody,
            @Part("mobile") phone_no: RequestBody,
            @Part("date_of_birth") date_of_birth: RequestBody,
            @Part("about_user") about_user: RequestBody,
            @Part("gender") gender: RequestBody,
            @Part("paystack_secret") paystack_secret: RequestBody,
            @Part("user_type") User_type: RequestBody,
            @Part profile_image: MultipartBody.Part): ModelLogin


    @FormUrlEncoded
    @POST("Apidata/get-user-vehicle")
    suspend fun getUserVehicle(
            @Field("token") token: String): ModelUserVehicles

   @FormUrlEncoded
    @POST("Apidata/get-states")
    suspend fun getStates(
            @Field("token") token: String): ModelStates


    @FormUrlEncoded
    @POST("Apidata/delete-vehicle")
    suspend fun deleteVehicle(
            @Field("token") token: String,
            @Field("vehicleId") vehicleId: String): ModelUserVehicles

    @FormUrlEncoded
    @POST("Apidata/get-vehicles")
    suspend fun getVehiclesAndType(
            @Field("token") token: String): ModelVehicleAndTypes

    @Multipart
    @POST("Apidata/add-user-vehicle")
    suspend fun addUserVehicle(
            @Part("token") token: RequestBody,
            @Part("vehicle_name") vehicle_name: RequestBody,
            @Part("vehicle_type") vehicle_type: RequestBody,
            @Part("model") model: RequestBody,
            @Part("plate_number") plate_number: RequestBody,
            @Part userfile: List<MultipartBody.Part>): ModelUserVehicles


    /***
     * Email verification module
     * ***/
    @FormUrlEncoded
    @POST("Apidata/get-user-emails")
    suspend fun getAllEmails(
            @Field("token") token: String): ModelGetAllEmails


    @FormUrlEncoded
    @POST("Apidata/save-user-email")
    suspend fun saveUserEmail(
            @Field("token") token: String,
            @Field("email") email: String): ModelCommon


    @FormUrlEncoded
    @POST("Apidata/action-email")   // use for delete, make primary
    suspend fun deleteEmail(
            @Field("token") token: String,
            @Field("email") email: String,
            @Field("type") type: String): ModelCommon


    @FormUrlEncoded
    @POST("Apidata/action-email")   // use for delete, make primary
    suspend fun resendVerificationEmail(
            @Field("token") token: String,
            @Field("email") email: String,
            @Field("type") type: String,
            @Field("emailType") emailType: String): ModelCommon

    /****
     * Id verifications
     * ****/
    @Multipart
    @POST("Apidata/save-user-identity")
    suspend fun saveUserIdentity(
            @Part("token") token: RequestBody,
            @Part("first_name_id") first_name_id: RequestBody,
            @Part("last_name_id") last_name_id: RequestBody,
            @Part idverify: MultipartBody.Part): ModelIdVerification


    @FormUrlEncoded
    @POST("Apidata/get-user-identity")
    suspend fun getVerifiedIds(
            @Field("token") token: String): ModelIdVerification

    /****
     * Notification settings
     * ****/
    @FormUrlEncoded
    @POST("Apidata/get-notification-settings")   // get notifications settings
    suspend fun getNotificationSettings(
            @Field("token") token: String): ModelNotificationSettings


    @FormUrlEncoded
    @POST("Apidata/save-notification-settings")   // save notification setting
    suspend fun saveNotificationSettings(
            @Field("token") token: String,
            @Field("sms") sms: String,
            @Field("email") email: String,
            @Field("push") push: String): ModelNotificationSettings

    /****
     * payouts
     * ****/
    @FormUrlEncoded
    @POST("Apidata/payments") //  get notifications settings
    suspend fun getPayouts(
            @Field("token") token: String): ModelPayouts


    /****
     * bank details
     * ****/
    @FormUrlEncoded
    @POST("Apidata/get-bank-detail") //  get notifications settings
    suspend fun getBankDetail(
            @Field("token") token: String): ModelSavedBankCards


    @FormUrlEncoded
    @POST("Apidata/get-bank-names") // Bank names
    suspend fun getBankNames(
            @Field("token") token: String): ModelBankNames


    @FormUrlEncoded
    @POST("Apidata/add-bank-detail")   // save notification setting
    suspend fun saveBankAccount(
            @Field("token") token: String,
            @Field("account_number") account_number: String,
            @Field("bank_user_name") bank_user_name: String,
            @Field("bank_name") bank_name: String
    ): ModelSavedBankCards


    @FormUrlEncoded
    @POST("Apidata/delete-bank-detail") //  get notifications settings
    suspend fun delBankCard(
            @Field("token") token: String,
            @Field("id") id: String): ModelSavedBankCards

    /****
     * About us
     * ****/
    @FormUrlEncoded
    @POST("Apidata/get-about") // get about us
    suspend fun getAboutUs(
            @Field("token") token: String): ModelAboutUs


    /****
     * Get help content
     * ****/
    @FormUrlEncoded
    @POST("Apidata/get-help-content")  //  get help content
    suspend fun getHelpContent(
            @Field("token") token: String): ModelHelp


    /****
     *  Promotion category
     * ****/
    @FormUrlEncoded
    @POST("Apidata/get-refered-users")  //  get rewards content
    suspend fun getRewards(
            @Field("token") token: String): ModelRewards

    @FormUrlEncoded
    @POST("Apidata/get-promo-codes")   //  get get offers content
    suspend fun getOffers(
            @Field("token") token: String): ModelOffers

    @FormUrlEncoded
    @POST("Apidata/get-refer-code")   //  get refer link
    suspend fun getReferCode(
            @Field("token") token: String): ModelReferCode

    /****
     *  Get all notifications
     * ****/
    @FormUrlEncoded
    @POST("Apidata/get-all-notifications") //  get all notifications
    suspend fun getAllNotifications(
            @Field("token") token: String): ModelAllNotifications


    /****
     *  Post a trip module starts
     * ****/
    @FormUrlEncoded
    @POST("Apidata/getpost-trip-status") //  get post trip  status whether the driveer can post trip or not
    suspend fun getPostTripStatus(
            @Field("token") token: String): ModelCommon

    @FormUrlEncoded
    @POST("Apidata/get-trips") // get trips
    suspend fun MyTrips(
            @Field("token") token: String,
            @Field("type") type: String): ModelMyTrips

    @FormUrlEncoded
    @POST("Apidata/get-trip-requests")
    suspend fun receiveRequests(
            @Field("token") token: String): ModelRequestReceived


    @FormUrlEncoded
    @POST("Apidata/view-post-trip")
    suspend fun viewPostTrip(
            @Field("token") token: String,
            @Field("tripId") tripId: String): ModelPostTripDetail


    @FormUrlEncoded
    @POST("Apidata/close-trip")   // Close trip:- status =1-close, 0 -Open
    suspend fun closeTrip(
            @Field("token") token: String,
            @Field("tripId") tripId: String,
            @Field("trip_status") trip_status: String): ModelCommon

    @FormUrlEncoded
    @POST("Apidata/cancel-trip")  // Cancel trip:- status  = 3
    suspend fun cancelTrip(
            @Field("token") token: String,
            @Field("tripId") tripId: String,
            @Field("trip_status") trip_status: String): ModelCommon

    @FormUrlEncoded
    @POST("Apidata/complete-trip")  // Complete trip:- status  = 2
    suspend fun markAsComplete(
            @Field("token") token: String,
            @Field("tripId") tripId: String,
            @Field("trip_status") trip_status: String): ModelCommon

    /* ***
     *  My Rides module
     * ***/
    @FormUrlEncoded
    @POST("Apidata/passenger-rides") // get rides - type -upcoming, recent
    suspend fun myRides(
            @Field("token") token: String,
            @Field("type") type: String): ModelMyRides

    @FormUrlEncoded
    @POST("Apidata/get-ride-detail")
    suspend fun viewRideDetail(
            @Field("token") token: String,
            @Field("request_id") request_id: String): ModelRideDetail


    @FormUrlEncoded
    @POST("Apidata/cancel-ride")  // Cancel ride by passenger
    suspend fun cancelRideByPassenger(
            @Field("token") token: String,
            @Field("requestId") requestId: String,
            @Field("status") status: String): ModelCommon


    @FormUrlEncoded
    @POST("Apidata/submit-review")  // Cancel ride by passenger
    suspend fun submitReviewByPassenger(
            @Field("token") token: String,
            @Field("request_id") request_id: String,
            @Field("rating") rating: String,
            @Field("posted_trip_id") posted_trip_id: String,
            @Field("posted_trip_stops_id") posted_trip_stops_id: String,
            @Field("requested_trip_id") requested_trip_id: String,
            @Field("passenger_id") passenger_id: String,
            @Field("driver_id") driver_id: String,
            @Field("review_by") review_by: String,
            @Field("comment") comment: String): ModelCommon

    /****
     * Inbox Modules
     * ****/
    @FormUrlEncoded
    @POST("Apidata/get-user-inbox")
    suspend fun getInbox(
            @Field("token") token: String): ModelInboxList

    @FormUrlEncoded
    @POST("Apidata/get-chat")
    suspend fun getAllChat(
            @Field("token") token: String,
            @Field("conversation_id") conversation_id: String): ModelAllChat

    @FormUrlEncoded
    @POST("Apidata/save-chat")
    suspend fun sendMessage(
            @Field("token") token: String,
            @Field("message") message: String,
            @Field("from_id") from_id: String,
            @Field("to_id") to_id: String,
            @Field("conversation_id") conversation_id: String): ModelSendMessage

    @FormUrlEncoded
    @POST("Apidata/get-recent-messages")
    suspend fun getRecentMessages(
            @Field("token") token: String,
            @Field("conversation_id") conversation_id: String): ModelAllChat


}