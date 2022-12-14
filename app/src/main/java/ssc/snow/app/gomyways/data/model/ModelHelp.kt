package ssc.snow.app.gomyways.data.model

data class ModelHelp(
        val `data`: List<Data?>? = listOf(),
        val message: String? = "",
        val status: Boolean
) {
    data class Data(
            val created_at: String? = "",
            val description: String? = "",
            val id: String? = "",
            val title: String? = ""
    )
}