package cn.iome.faceme.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import cn.iome.faceme.function.Consumer;

/**
 * Created by haoping on 17/5/3.
 * TODO
 */
public class UIUtil {

    public static void showDialog(Context context, String title, String message, String positive, String negative, boolean canceledOnTouchOutside, final Consumer<DialogInterface> callback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.accept(dialog);
            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.accept(dialog);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        alertDialog.show();
    }

}
