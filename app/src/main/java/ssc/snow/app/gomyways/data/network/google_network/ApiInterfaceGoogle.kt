package ssc.snow.app.gomyways.data.network.google_network

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterfaceGoogle {

    @GET("json") // get routes json
    suspend fun getRoutes(
            @QueryMap options: Map<String, String>?): JsonObject


    @GET("json?origin=10.3181466,123.9029382&destination=10.311795,123.915864&key=AIzaSyBvnYPa4tw9s5TSGwzePeWD4Kk7yulyy9c") // get routes json
    suspend fun getRoutesStatic(): JsonObject


}