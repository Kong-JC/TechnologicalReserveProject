package com.example.technologicalreserveproject;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

public class PermissionUtils {

    private static final String TAG = "PermissionUtils";

    public static final int VOICE_PERMISSION_REQUEST_CODE = 0x00123;

    /**
     * 请求所有权限
     *
     * @param context
     * @return true 代表有需要申请的权限 false 代表没有需要申请的权限
     */
    public static boolean initPermissionRequest(Activity context) {
        String permissions[] = {
//                Manifest.permission.INTERNET,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.MODIFY_AUDIO_SETTINGS,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.ACCESS_NETWORK_STATE,
//                Manifest.permission.ACCESS_WIFI_STATE,
//                Manifest.permission.READ_LOGS,
//                Manifest.permission.REQUEST_INSTALL_PACKAGES
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.FLASHLIGHT,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ArrayList<String> toApplyList = PermissionRequest(context, permissions);
        return toApplyList.size() != 0 ? true : false;
    }

    /**
     * 语音识别权限请求
     *
     * @param context
     * @return true 代表有需要申请的权限 false 代表没有需要申请的权限
     */
    public static boolean voicePermissionRequest(Activity context) {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ArrayList<String> toApplyList = PermissionRequest(context, permissions);
        return toApplyList.size() != 0 ? true : false;
    }

    // 录音权限
    public static boolean mp3RecordPermissionRequest(Activity context) {
        String permissions[] = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS
        };
        ArrayList<String> toApplyList = PermissionRequest(context, permissions);
        return toApplyList.size() != 0 ? true : false;
    }

    // 存储权限
    public static boolean WriteStoragePermissionRequest(Activity context) {
        String permissions[] = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ArrayList<String> toApplyList = PermissionRequest(context, permissions);
        return toApplyList.size() != 0 ? true : false;
    }

    @NonNull
    private static ArrayList<String> PermissionRequest(Activity context, String[] permissions) {
        ArrayList<String> toApplyList = new ArrayList<>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
                Log.d(TAG, " -=- initPermission: 没有权限，添加到集合并申请:" + perm);
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(context,
                    toApplyList.toArray(tmpList), VOICE_PERMISSION_REQUEST_CODE);
        }
        return toApplyList;
    }

}
