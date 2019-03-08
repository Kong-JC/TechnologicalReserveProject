package com.example.technologicalreserveproject.addDelItemList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.technologicalreserveproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDelItemAdapter extends RecyclerView.Adapter<AddDelItemAdapter.ViewHolder> {

    private List<String> frequencyOrders;
    private List<String> frequencyPriors;
    private List<String> frequencyValues;
    private int count;
    private Context context;

    public AddDelItemAdapter(Context context) {
        this.context = context;
        count++;
        frequencyOrders = new ArrayList<>();
        frequencyPriors = new ArrayList<>();
        frequencyValues = new ArrayList<>();
        addEntity(count, "", "");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.adapter_add_del_item_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int p) {

        String order = frequencyOrders.get(p);
        String prior = frequencyPriors.get(p);
        String value = frequencyValues.get(p);

        h.tvNumber.setText(order);
        h.etPriority.setText(prior);
        h.etFrequency.setText(value);

        h.etPriority.setTag(h.getLayoutPosition());
        h.etPriority.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((Integer) h.etPriority.getTag() == h.getLayoutPosition() && h.etPriority.hasFocus()) {
                    frequencyPriors.set(h.getLayoutPosition(), s.toString());
                }
            }
        });

        h.etFrequency.setTag(h.getLayoutPosition());
        h.etFrequency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((Integer) h.etFrequency.getTag() == h.getLayoutPosition() && h.etFrequency.hasFocus()) {
                    frequencyValues.set(h.getLayoutPosition(), s.toString());
                }
            }
        });

        h.icon.setOnClickListener(v -> {
            if (p == 0) addEntity(++count, "", "");
            else delEntity(p);
        });

    }

    private void addEntity(int order, String prior, String value) {
        frequencyOrders.add(order + "");
        frequencyPriors.add(prior);
        frequencyValues.add(value);
        notifyItemInserted(getItemCount());
    }

    private void delEntity(int p) {
        frequencyOrders.remove(p);
        frequencyPriors.remove(p);
        frequencyValues.remove(p);
        count--;
        for (int i = 0; i < frequencyOrders.size(); i++) {
            frequencyOrders.set(i, i + 1 + "");
        }
        notifyDataSetChanged();
//        notifyItemChanged(p,getItemCount());
    }

    @Override
    public int getItemCount() {
        return frequencyOrders.size();
    }

    public Map<String, String[]> getData() {
        String orders;
        String priors;
        String values;
        String msg = null;
        for (int i = 0; i < frequencyOrders.size(); i++) {
            orders = frequencyOrders.get(i);
            priors = frequencyPriors.get(i);
            values = frequencyValues.get(i);
            if (TextUtils.isEmpty(priors)) {
                msg = "请输入序号" + orders + "的优先级";
                break;
            }
            if (TextUtils.isEmpty(values)) {
                msg = "请输入序号" + orders + "的频率";
                break;
            }
        }
        if (msg != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            return null;
        }

        Map<String, String[]> data = new HashMap<>();
        data.put("frequencyOrder", frequencyOrders.toArray(new String[frequencyOrders.size()]));
        data.put("frequencyPrior", frequencyPriors.toArray(new String[frequencyPriors.size()]));
        data.put("frequencyValue", frequencyValues.toArray(new String[frequencyValues.size()]));

        return data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber;
        EditText etPriority, etFrequency;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            etPriority = itemView.findViewById(R.id.etPriority);
            etFrequency = itemView.findViewById(R.id.etFrequency);
            icon = itemView.findViewById(R.id.icon);
        }
    }

    interface ItemChange {
        void itemChange();
    }

    ItemChange itemChange;

    public void setItemChange(ItemChange itemChange) {
        this.itemChange = itemChange;
    }

}
