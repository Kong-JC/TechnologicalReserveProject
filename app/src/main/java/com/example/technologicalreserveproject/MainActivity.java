package com.example.technologicalreserveproject;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent() {
        PermissionUtils.initPermissionRequest(this);
    }


}
