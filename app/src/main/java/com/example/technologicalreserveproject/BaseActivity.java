package com.example.technologicalreserveproject;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    public void toast(String msg){
        toast(msg, Toast.LENGTH_SHORT);
    }

    public void toast(String msg,int length){
        Toast.makeText(this, msg, length).show();
    }

}
