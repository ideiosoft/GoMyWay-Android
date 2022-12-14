package ssc.snow.app.gomyways.data.network.google_network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory.create
import java.util.concurrent.TimeUnit

object RetrofitClientGoogle {

    private val REQUEST_TIMEOUT = 60
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {

        if (okHttpClient == null)
            initOkHttp()


        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient!!)
                    .addConverterFactory(create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        return retrofit
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor { chain ->
            val original = chain.request()

            val mToken = ""

            //    if (new SessionGomyway(mContext).getLoggedInUserDetail() != null) {
            //           mToken = new SessionGomyway(mContext).getLoggedInUserDetail().getToken();
            //                    Log.wtf("request_auth_token", mToken);
            //      }

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
            // .header("Authorization", "Bearer " + mToken); // <-- this is the important line

            val request = requestBuilder.build()

            chain.proceed(request)
        }

        okHttpClient = httpClient.build()
    }


}
