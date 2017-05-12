package cn.hawk.commonlib.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by kehao.wei on 2016/5/12.
 */
public class PermissionUtils {
    private final static String TAG = PermissionUtils.class.getSimpleName();

    public static boolean checkPermission(final Activity context, final String[] permissions, String message, final int requestCode) {
        final ArrayList<String> needPermissions = new ArrayList<String>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                needPermissions.add(permission);
            }
        }
        LogUtils.getInstance().logd(TAG, "need permissions size : " + needPermissions.size());
        if (needPermissions.size() > 0) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                showMessageOkCancel(context, message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(context,
                                        needPermissions.toArray(new String[needPermissions.size()]),
                                        requestCode);
                            }
                        });
                LogUtils.getInstance().logd(TAG, "show dialog here");
                return false;
            }
            LogUtils.getInstance().logd(TAG, "let system to show dialog");
            ActivityCompat.requestPermissions(context,
                    needPermissions.toArray(new String[needPermissions.size()]),
                    requestCode);
            return false;
        }
        return true;

    }

    private static void showMessageOkCancel(Activity context, String message,
                                            DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context).setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", null).create().show();
    }

    public static boolean checkPermissionResults(String[] permissions, int[] grantResults) {
        if (permissions.length != grantResults.length)
            return false;
        int size = permissions.length;
        for (int i = 0; i < size; i++) {
            if (grantResults[i] != 0)
                return false;
        }
        return true;
    }
}
