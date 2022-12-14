package ssc.snow.app.gomyways.data.model

data class ModelUserDetail(val `data`: Data? = Data(), val message: String, val status: Boolean) {


    data class Data(val user: User? = User()) {

        data class User(
                val about_user: String? = "",
                val address: String? = "",
                val created: String? = "",
                val date_of_birth: String? = "",
                val email: String? = "",
                val first_name: String? = "",
                val gender: String? = "",
                val id: String? = "",
                val last_name: String? = "",
                val latitude: Any? = Any(),
                val locale: Any? = Any(),
                val longitude: Any? = Any(),
                val mobile: String? = "",
                val mobile_status: String? = "",
                val modified: String? = "",
                val oauth_provider: Any? = Any(),
                val oauth_uid: Any? = Any(),
                val password: String? = "",
                val profile_url: String? = "",
                val refer_code: String? = "",
                val status: String? = "",
                val token: String? = "",
                val user_type: String? = "",
                val username: String? = "",
                val uuid: String? = "",
                val zipcode: String? = "")
    }
}