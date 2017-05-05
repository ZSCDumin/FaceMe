package cn.iome.fakotlin.utility

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

import cn.iome.fakotlin.function.Consumer

/**
 * Created by haoping on 17/5/3.
 * TODO
 */
object UIUtil {

    fun showDialog(context: Context, title: String, message: String, positive: String, negative: String, canceledOnTouchOutside: Boolean, callback: Consumer<DialogInterface>) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positive) { dialog, which -> callback.accept(dialog) }
        builder.setNegativeButton(negative) { dialog, which -> callback.accept(dialog) }
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        alertDialog.show()
    }

}
