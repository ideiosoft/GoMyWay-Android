package ssc.snow.app.gomyways.data.model

data class ModelPostTripDetail(
        val `data`: Data? = Data(),
        val message: String? = "",
        val status: Boolean
) {
    data class Data(
            val trip_stop_points: List<TripStopPoint?>? = listOf(),
            val trip_stop_routes: List<TripStopRoute?>? = listOf(),
            val viewTrip: ViewTrip? = ViewTrip()
    ) {

        data class TripStopPoint(
                val created_at: String? = "",
                val deleted_at: Any? = Any(),
                val id: String? = "",
                val posted_trip_id: String? = "",
                val price_per_seat: Any? = Any(),
                val stop_point: String? = "",
                val updated_at: Any? = Any()
        )

        data class TripStopRoute(
                val created_at: String? = "",
                val id: String? = "",
                val origin_point: String? = "",
                val posted_trip_id: String? = "",
                val price_per_seat: String? = "",
                val stop_point: String? = ""
        )

        data class ViewTrip(
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
                val id: String? = "",
                val leaving: String? = "",
                val luggage: String? = "",
                val model: String? = "",
                val origin: String? = "",
                val others: String? = "",
                val plate_number: String? = "",
                val price_per_seat: String? = "",
                val returning: Any? = Any(),
                val total_seats: String? = "",
                val trip_id: String? = "",
                val trip_status: String? = "",
                val trip_type: String? = "",
                val updated_at: String? = "",
                val user_id: String? = "",
                val user_vehicle_id: String? = "",
                val username: String? = "",
                val vehicle_id: String? = "",
                val vehicle_name: String? = "",
                val vehicle_type: String? = "",
                val vehicle_type_id: String? = "",
                val vehicle_url: String? = "",
                val vehicle_url_path: String? = "",
                val views: Any? = Any()

        )
    }
}