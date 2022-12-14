package ssc.snow.app.gomyways.data.model

data class ModelPayouts(
        val `data`: List<Data?>? = listOf(),
        val message: String? = "",
        val status: Boolean
) {
    data class Data(
            val available_seats: String? = "",
            val back_row_seating: String? = "",
            val booking_preference: String? = "",
            val created_at: String? = "",
            val deleted_at: Any? = Any(),
            val description: String? = "",
            val destination: String? = "",
            val discount: Any? = Any(),
            val driver_id: String? = "",
            val email: String? = "",
            val event_id: Any? = Any(),
            val first_name: String? = "",
            val id: String? = "",
            val last_name: String? = "",
            val leaving: String? = "",
            val luggage: String? = "",
            val origin: String? = "",
            val others: String? = "",
            val passenger_id: String? = "",
            val payment_status: String? = "",
            val posted_trip_id: String? = "",
            val posted_trip_stops_id: String? = "",
            val price_per_seat: String? = "",
            val refund_amount: Any? = Any(),
            val request_id: String? = "",
            val returning: Any? = Any(),
            val seats_amount: String? = "",
            val seats_booked: String? = "",
            val service_charges: String? = "",
            val total_amount: String? = "",
            val total_seats: String? = "",
            val transaction_id: String? = "",
            val trip_status: String? = "",
            val trip_type: String? = "",
            val updated_at: Any? = Any(),
            val user_id: String? = "",
            val user_vehicle_id: String? = "",
            val views: Any? = Any()
    )
}