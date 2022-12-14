package ssc.snow.app.gomyways.data.model

data class ModelGetAllEmails(
    val `data`: Data? = Data(),
    val message: String? = "",
    val status: Boolean
) {
    data class Data(
        val other_emails: List<OtherEmail?>? = listOf(),
        val primary_email: PrimaryEmail? = PrimaryEmail()
    ) {
        data class OtherEmail(
            val created_at: String? = "",
            val deleted_at: String? = "",
            val email: String? = "",
            val id: String? = "",
            val status: String? = "",
            val type: String? = "",
            val updated_at: String? = "",
            val user_id: String? = "",
            val username: String? = "",
            val verification_code: String? = ""
        )

        data class PrimaryEmail(
            val about_user: String? = "",
            val address: Any? = Any(),
            val created: String? = "",
            val date_of_birth: String? = "",
            val driver_suspended_on: String? = "",
            val email: String? = "",
            val email_status: String? = "",
            val first_name: String? = "",
            val forgot_password_link: String? = "",
            val forgot_password_link_time: String? = "",
            val gender: String? = "",
            val id: String? = "",
            val last_name: String? = "",
            val latitude: Any? = Any(),
            val locale: Any? = Any(),
            val login_status: String? = "",
            val longitude: Any? = Any(),
            val mobile: String? = "",
            val mobile_status: String? = "",
            val modified: String? = "",
            val oauth_provider: String? = "",
            val oauth_uid: Any? = Any(),
            val password: String? = "",
            val paystack_secret: String? = "",
            val profile_url: String? = "",
            val refer_code: String? = "",
            val status: String? = "",
            val token: String? = "",
            val user_type: String? = "",
            val username: String? = "",
            val uuid: String? = "",
            val verification_code: String? = "",
            val zipcode: String? = ""
        )
    }
}