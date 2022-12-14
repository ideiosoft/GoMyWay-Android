package ssc.snow.app.gomyways.data.model.inbox

data class ModelSendMessage(
        val `data`: Message? = Message(),
        val message: String? = "",
        val status: Boolean? = false
)