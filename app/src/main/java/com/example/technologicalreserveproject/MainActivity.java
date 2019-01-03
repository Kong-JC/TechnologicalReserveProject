package com.example.technologicalreserveproject;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.technologicalreserveproject.livePublisher.LivePublisherActivity;
import com.example.technologicalreserveproject.location.GPSUtils;
import com.nestia.biometriclib.BiometricPromptManager;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;


public class MainActivity extends BaseActivity {

//    private BiometricPromptManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.initPermissionRequest(this);
    }

    boolean isLocation;

    public void gpsLocation(View v) {
        StringBuilder sb = new StringBuilder();
        GPSUtils gpsUtils = new GPSUtils(MainActivity.this);
        TextView textView = findViewById(R.id.textView);
        if (!isLocation) {
            Location location = gpsUtils.getLocation();
            sb.append("经度:" + location.getLongitude() + "  纬度:" + location.getLatitude() + "\n");
            String address = gpsUtils.getAddressStr();
            sb.append("地址:" + address);
            textView.setText(sb.toString());
        } else {
            gpsUtils.removeListener();
            textView.setText("");
        }
        isLocation = !isLocation;
    }

    public void biometricDiscern(View v) {
        final TextView textView = findViewById(R.id.textView1);

        BiometricPromptManager mManager = BiometricPromptManager.from(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SDK version is " + Build.VERSION.SDK_INT + "\n");
        stringBuilder.append("是否支持指纹识别: " + mManager.isHardwareDetected() + "\n");
        stringBuilder.append("是否设置了指纹: " + mManager.hasEnrolledFingerprints() + "\n");
        stringBuilder.append("系统有没有设置锁屏: " + mManager.isKeyguardSecure() + "\n");

        if (mManager.isBiometricPromptEnable()) {
            mManager.authenticate(new BiometricPromptManager.OnBiometricIdentifyCallback() {
                @Override
                public void onUsePassword() {
                    textView.setText("onUsePassword");
                }

                @Override
                public void onSucceeded() {
                    textView.setText("onSucceeded");
                }

                @Override
                public void onFailed() {
                    textView.setText("onFailed");
                }

                @Override
                public void onError(int code, String reason) {
                    textView.setText("onError");
                }

                @Override
                public void onCancel() {
                    textView.setText("onCancel");
                }
            });
        } else {
            if (mManager.isHardwareDetected()) {
                StringBuilder sb = new StringBuilder();
                if (!mManager.hasEnrolledFingerprints()) sb.append("该设备没有登记指纹\n");
                if (!mManager.isKeyguardSecure()) sb.append("该设备没有设置锁屏\n");
                sb.append("请添加指纹!");
//                toast(sb.toString());
                textView.setText(sb.toString());
//                startActivity(new Intent(Settings.ACTION_SETTINGS));
            } else textView.setText("该设备不支持指纹设别");
        }
    }

    public void startLiveActivity(View v) {
        EditText editText = findViewById(R.id.editText);
//        editText.setText("rtmp://192.168.101.124:1935/live/stream");
        Intent intent = new Intent(this, LivePublisherActivity.class);
        intent.putExtra("pubUrl", editText.getText().toString());
        startActivity(intent);
    }

    public void startLiveDemoActivity(View v) {
        CityPicker.from(getSupportFragmentManager())
//                .enableAnimation(enable)
//                .setAnimationStyle(anim)
                .setLocatedCity(null)
//                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
//                        currentTV.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
                        Toast.makeText(
                                getApplicationContext(),
                                String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CityPicker.from(getSupportFragmentManager()).locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                            }
                        }, 3000);
                    }
                })
                .show();
    }

}
