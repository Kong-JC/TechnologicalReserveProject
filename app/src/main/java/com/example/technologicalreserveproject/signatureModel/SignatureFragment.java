package com.example.technologicalreserveproject.signatureModel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.technologicalreserveproject.BaseFragment;
import com.example.technologicalreserveproject.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SignatureFragment extends BaseFragment {

    @BindView(R.id.signatureView)
    SignatureView signatureView;
    @BindView(R.id.addImage)
    Button addImage;
    @BindView(R.id.switchMode)
    Button switchMode;
    @BindView(R.id.closePath)
    Button closePath;

    private boolean isSignature;

    @Override
    protected int setContentView() {
        return R.layout.fragment_signature;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        switchMode.setText(isSignature?"签名模式":"观察模式");
    }

    @OnClick({R.id.addImage, R.id.switchMode, R.id.closePath})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addImage:
                break;
            case R.id.switchMode:
                changeModel();
                signatureView.changeModel(isSignature);
                break;
            case R.id.closePath:
                signatureView.pathClose();
                break;
        }
    }

    private void changeModel(){
        isSignature = !isSignature;
        switchMode.setText(isSignature?"签名模式":"观察模式");
    }

}
