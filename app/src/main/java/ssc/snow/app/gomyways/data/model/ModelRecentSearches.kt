package ssc.snow.app.gomyways.data.model

data class ModelRecentSearches(
    val `data`: List<Data?>? = listOf(),
    val message: String? = "",
    val status: Boolean? = false
) {
    data class Data(
        val created_at: String? = "",
        val deleted_at: Any? = Any(),
        val description: Any? = Any(),
        val destination: String? = "",
        val id: String? = "",
        val leaving_date: String? = "",
        val origin: String? = "",
        val passenger_id: String? = "",
        val seats_required: Any? = Any(),
        val updated_at: Any? = Any()
    )
}