package ssc.snow.app.gomyways.data.model

data class ModelEmails(
        val `data`: List<Data?>? = listOf(),
        val message: String? = "",
        val status: Boolean
) {
    data class Data(
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
}