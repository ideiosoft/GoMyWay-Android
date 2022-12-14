package ssc.snow.app.gomyways.data.model

data class ModelTerms(
    val `data`: Data? = Data(),
    val message: String? = "",
    val status: Boolean? = false
) {
    data class Data(
        val created_at: String? = "",
        val description: String? = "",
        val id: String? = "",
        val slug: String? = "",
        val title: String? = "",
        val updated_at: String? = ""
    )
}