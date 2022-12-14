package ssc.snow.app.gomyways.data.model.inbox

data class ModelInboxList(
    val `data`: List<Data?>? = listOf(),
    val message: String? = "",
    val status: Boolean? = false
) {
    data class Data(
        val conversation_id: String? = "",
        val created_at: String? = "",
        val first_name: String? = "",
        val from_user_id: String? = "",
        val id: String? = "",
        val last_message: String? = "",
        val last_name: String? = "",
        var new_messages: Int? = 0,
        val notification: String? = "",
        val notification_link: Any? = Any(),
        val profile_image: String? = "",
        val profile_url: String? = "",
        val time: String? = "",
        val time_string: Any? = "0",
        val updated_at: Any? = Any(),
        val user_id: String? = "",
        val username: String? = ""
    )
}