package ssc.snow.app.gomyways.data.utility

import android.view.View
import android.widget.Toast

class MyKotlinClass {


    fun main(mArg: Array<String>) {
        println()

    }


    fun View.showMsg(mMd: String) {
        Toast.makeText(this.context, mMd, Toast.LENGTH_SHORT).show()
    }

}
