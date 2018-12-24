package com.example.technologicalreserveproject;

import android.app.Activity;
import android.widget.Toast;

public class BaseActivity extends Activity {

    public void toast(String msg){
        toast(msg, Toast.LENGTH_SHORT);
    }

    public void toast(String msg,int length){
        Toast.makeText(this, msg, length).show();
    }

}
