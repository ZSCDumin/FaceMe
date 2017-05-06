package cn.iome.fakotlin.utility

import android.os.Environment

/**
 * Created by haoping on 17/4/8.
 * TODO
 */
interface Constants {

    companion object {
        //设置APPID/AK/SK
        val APP_ID = "9492882"
        val API_KEY = "kSGKuH9bpInU0UWzIkb7DT1A"
        val SECRET_KEY = "BiM1j2xzIsEF3Gw2OGhEyrsBCjvg6lmP"
        val test1 = Environment.getExternalStorageDirectory().absolutePath + "/test1.jpg"
        val test2 = Environment.getExternalStorageDirectory().absolutePath + "/test2.jpg"
    }

}
