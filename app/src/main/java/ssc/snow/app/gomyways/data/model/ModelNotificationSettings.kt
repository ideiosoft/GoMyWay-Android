package ssc.snow.app.gomyways.data.model

data class ModelNotificationSettings(
    val `data`: List<Data?>? = listOf(),
    val message: String? = "",
    val status: Boolean
) {
    data class Data(
        val created_at: String? = "",
        val id: String? = "",
        val notification_type_id: String? = "",
        val status: String? = "",
        val user_id: String? = "",
        val username: String? = ""
    )
}