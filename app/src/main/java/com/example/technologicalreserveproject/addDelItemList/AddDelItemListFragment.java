package com.example.technologicalreserveproject.addDelItemList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.technologicalreserveproject.BaseFragment;
import com.example.technologicalreserveproject.R;
import com.google.gson.Gson;

import java.util.Map;

import butterknife.BindView;

public class AddDelItemListFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int setContentView() {
        return R.layout.fragment_add_del_item_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        AddDelItemAdapter adapter = new AddDelItemAdapter(mContext);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
//        adapter.setItemChange(() -> Utils.smoothMoveToPosition(recyclerView, adapter.getItemCount()));

        toolbar.setNavigationOnClickListener(v -> {
            Map<String, String[]> data = adapter.getData();
            if (data != null) {
                toastShort("数据获取成功");
                Log.d("AddDelItemListFragment", " -=-=- initView: json:" + new Gson().toJson(data));
            }
        });
    }

}
