package ssc.snow.app.gomyways.data.model

data class ModelMyTrips(
        val `data`: List<Data?>? = listOf(),
        val message: String? = "",
        val status: Boolean =false
) {
    data class Data(
            val available_seats: String? = "",
            val pending_seats: String? = "",
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
            val trip_id: String = "",
            val trip_status: String? = "",
            val trip_type: String? = "",
            val updated_at: Any? = Any(),
            val user_vehicle_id: String? = "",
            val username: String? = "",
            val views: Any? = Any(),
            val bookings: Int? = 0
    )
}