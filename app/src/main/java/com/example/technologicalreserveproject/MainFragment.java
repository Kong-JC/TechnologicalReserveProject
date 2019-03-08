package com.example.technologicalreserveproject;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.technologicalreserveproject.livePublisher.LivePublisherActivity;
import com.example.technologicalreserveproject.location.GPSUtils;
import com.nestia.biometriclib.BiometricPromptManager;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView1)
    TextView textView1;

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.button:
                gpsLocation();
                break;
            case R.id.button2:
                biometricDiscern();
                break;
            case R.id.button3:
                startLiveActivity();
                break;
            case R.id.button4:
                startLiveDemoActivity();
                break;
            case R.id.button5:
                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_addDelItemListFragment);
                break;
            case R.id.button6:
                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_signatureFragment);
                break;
        }
    }

    boolean isLocation;

    public void gpsLocation() {
        StringBuilder sb = new StringBuilder();
        GPSUtils gpsUtils = new GPSUtils(getActivity());
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

    public void biometricDiscern() {
        BiometricPromptManager mManager = BiometricPromptManager.from(getActivity());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SDK version is " + Build.VERSION.SDK_INT + "\n");
        stringBuilder.append("是否支持指纹识别: " + mManager.isHardwareDetected() + "\n");
        stringBuilder.append("是否设置了指纹: " + mManager.hasEnrolledFingerprints() + "\n");
        stringBuilder.append("系统有没有设置锁屏: " + mManager.isKeyguardSecure() + "\n");

        if (mManager.isBiometricPromptEnable()) {
            mManager.authenticate(new BiometricPromptManager.OnBiometricIdentifyCallback() {
                @Override
                public void onUsePassword() {
                    textView1.setText("onUsePassword");
                }

                @Override
                public void onSucceeded() {
                    textView1.setText("onSucceeded");
                }

                @Override
                public void onFailed() {
                    textView1.setText("onFailed");
                }

                @Override
                public void onError(int code, String reason) {
                    textView1.setText("onError");
                }

                @Override
                public void onCancel() {
                    textView1.setText("onCancel");
                }
            });
        } else {
            if (mManager.isHardwareDetected()) {
                StringBuilder sb = new StringBuilder();
                if (!mManager.hasEnrolledFingerprints()) sb.append("该设备没有登记指纹\n");
                if (!mManager.isKeyguardSecure()) sb.append("该设备没有设置锁屏\n");
                sb.append("请添加指纹!");
//                toast(sb.toString());
                textView1.setText(sb.toString());
//                startActivity(new Intent(Settings.ACTION_SETTINGS));
            } else textView1.setText("该设备不支持指纹设别");
        }
    }

    public void startLiveActivity() {
//        editText.setText("rtmp://192.168.101.124:1935/live/stream");
        Intent intent = new Intent(mContext, LivePublisherActivity.class);
        intent.putExtra("pubUrl", editText.getText().toString());
        startActivity(intent);
    }

    public void startLiveDemoActivity() {
        CityPicker.from(getChildFragmentManager())
//                .enableAnimation(enable)
//                .setAnimationStyle(anim)
                .setLocatedCity(null)
//                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
//                        currentTV.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
                        toastShort(String.format("点击的数据：%s，%s", data.getName(), data.getCode()));
                    }

                    @Override
                    public void onCancel() {
                        toastShort("取消选择");
                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
                        new Handler().postDelayed(() -> CityPicker.from(getChildFragmentManager()).locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS), 3000);
                    }
                }).show();
    }


}
