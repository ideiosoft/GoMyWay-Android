package ssc.snow.app.gomyways.data.model.inbox

data class ModelAllChat(
        val `data`: ArrayList<Message?>? = arrayListOf(),
        val message: String? = "",
        val status: Boolean? = false
)
