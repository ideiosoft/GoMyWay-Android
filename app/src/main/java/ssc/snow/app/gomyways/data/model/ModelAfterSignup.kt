package ssc.snow.app.gomyways.data.model

data class ModelAfterSignup(
    val `data`: Data? ,
    val message: String? = "",
    val status: Boolean? = false

) {
    data class Data(
        val message: String? = "",
        val user: User? = User(),
        val verification_code: String? = ""
    ) {
        data class User(
            val about_user: Any? = Any(),
            val address: Any? = Any(),
            val created: String? = "",
            val date_of_birth: Any? = Any(),
            val device_token: Any? = Any(),
            val device_token_ios: Any? = Any(),
            val driver_suspended_on: Any? = Any(),
            val email: String? = "",
            val email_status: String? = "",
            val first_name: String? = "",
            val forgot_password_link: Any? = Any(),
            val forgot_password_link_time: Any? = Any(),
            val gender: Any? = Any(),
            val id: String? = "",
            val last_name: String? = "",
            val latitude: Any? = Any(),
            val locale: Any? = Any(),
            val login_status: String? = "",
            val longitude: Any? = Any(),
            val mobile: Any? = Any(),
            val mobile_status: String? = "",
            val modified: String? = "",
            val oauth_provider: String? = "",
            val oauth_uid: Any? = Any(),
            val password: String? = "",
            val paystack_secret: Any? = Any(),
            val profile_url: Any? = Any(),
            val refer_code: String? = "",
            val status: String? = "",
            val token: String? = "",
            val user_type: String? = "",
            val username: String? = "",
            val uuid: Any? = Any(),
            val verification_code: String? = "",
            val verification_code_mobile: Any? = Any(),
            val zipcode: String? = ""
        )
    }
}