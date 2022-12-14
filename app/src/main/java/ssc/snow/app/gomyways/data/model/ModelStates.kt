package ssc.snow.app.gomyways.data.model

data class ModelStates(
    val `data`: List<Data?>? = listOf(),
    val message: String? = "",
    val status: Boolean? = false
) {
    data class Data(
        val id: String? = "",
        val name: String? = ""
    )
}