package ssc.snow.app.gomyways.data.model

data class ModelLogin(
        val `data`: Data? = Data(),
        val message: String? = "",
        val status: Boolean
) {
    data class Data(
            val upcoming_trips: List<UpcomingTrip?>? = listOf(),
            val user: User? = User()
    ) {

        data class UpcomingTrip(
                val available_seats: String? = "",
                val back_row_seating: String? = "",
                val booking_preference: String? = "",
                val created_at: String? = "",
                val deleted_at: Any? = Any(),
                val description: String? = "",
                val destination: String? = "",
                val driver_id: String? = "",
                val event_id: Any? = Any(),
                val first_name: String? = "",
                val id: String? = "",
                val last_name: String? = "",
                val leaving: String? = "",
                val luggage: String? = "",
                val origin: String? = "",
                val others: String? = "",
                val price_per_seat: String? = "",
                val profile_url: String? = "",
                val returning: Any? = Any(),
                val total_seats: String? = "",
                val trip_id: String? = "",
                val trip_status: String? = "",
                val trip_type: String? = "",
                val updated_at: Any? = Any(),
                val user_vehicle_id: String? = "",
                val username: String? = "",
                val views: Any? = Any()
        )

        data class User(
                val about_user: String? = "",
                val number_of_reviews: Int? = 0,
                val avgRating: Int? = 0,
                val address: Any? = Any(),
                val age: Int? = 0,
                val created: String? = "",
                val date_of_birth: String? = "",
                val driver_suspended_on: Any? = Any(),
                val email: String? = "",
                val email_status: String? = "",
                val first_name: String? = "",
                val forgot_password_link: Any? = Any(),
                val forgot_password_link_time: Any? = Any(),
                val gender: String? = "",
                val goverment_issued_id: String? = "",
                val id: String? = "",
                val joined: String? = "",
                val last_name: String? = "",
                val latitude: Any? = Any(),
                val locale: Any? = Any(),
                val longitude: Any? = Any(),
                val mobile: String? = "",
                var mobile_status: String? = "",
                val modified: String? = "",
                val oauth_provider: String? = "",
                val oauth_uid: Any? = Any(),
                val password: String? = "",
                val paystack_secret: String? = "",
                val profile_image: String? = "",
                val profile_url: String? = "",
                val refer_code: String? = "",
                val status: String? = "",
                val token: String = "",
                val user_type: String? = "",
                val username: String? = "",
                val uuid: String? = "",
                val house_number: String? = null ?: "",
                val city_name: String? =  null ?: "",
                val state_name: String? =  null ?: "",
                val street_name: String? = null ?: "",
                val verification_code: Any? = Any(),
                val zipcode: String? = ""


        )
    }
}
