package ssc.snow.app.gomyways.views

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.facebook.FacebookSdk
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp
import io.kommunicate.Kommunicate
import ssc.snow.app.gomyways.data.utility.InjectorUtil


class MyApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {


    override fun onCreate() {
        super.onCreate()


        // facebook initialisations
        FacebookSdk.sdkInitialize(this)
        FirebaseApp.initializeApp(this)

        // Initialize the SDK
        Places.initialize(applicationContext, InjectorUtil.GOOGLE_API_KEY)
        // App Id: 17926f68062d6c997debb127ff9016ffb
        // Api key: cduxxoPn2li5qgnkU4UKqHfqfjO66DZi
        Kommunicate.init(this, "17926f68062d6c997debb127ff9016ffb")

    }

    init {
        instance = this
        // register the activity life cycle for getting the context
        registerActivityLifecycleCallbacks(this)
    }

    companion object {

        private var instance: MyApplication? = null
        private var mActivityContext: Activity? = null


        fun applicationContext(): MyApplication {
            return instance as MyApplication
        }

        fun activityContext(): Activity {
            return mActivityContext as Activity
        }
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {
        mActivityContext = activity
    }

    override fun onActivityDestroyed(activity: Activity) {
            }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

}



