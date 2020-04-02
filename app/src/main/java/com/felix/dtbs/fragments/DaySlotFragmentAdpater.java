package com.felix.dtbs.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.felix.dtbs.CommonUtil;
import com.felix.dtbs.R;

import java.util.List;
import java.util.Map;

public class DaySlotFragmentAdpater extends ArrayAdapter<Map<String, Object>> {
    public DaySlotFragmentAdpater(@NonNull Context context, int resource, @NonNull List<Map<String, Object>> data) {
        super(context, resource, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Map<String, Object> oneDayData = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_day_slot_item, parent, false);
        }

        TextView tvSlotTime = convertView.findViewById(R.id.tvSlotTime);
        tvSlotTime.setText(oneDayData.get(CommonUtil.DaySlotFragmentConst.TimeKey).toString());

        TextView tvSlotCount = convertView.findViewById(R.id.tvSlotTimeCount);
        tvSlotCount.setText(oneDayData.get(CommonUtil.DaySlotFragmentConst.NumberKey).toString());

        ProgressBar pb = convertView.findViewById(R.id.pbSlotBook);
        int color = (int)oneDayData.get(CommonUtil.DaySlotFragmentConst.ColorKey);
        pb.setProgressTintList(ColorStateList.valueOf(color));
        pb.setMax(CommonUtil.MAX_SLOT);
        pb.setProgress(Integer.parseInt(oneDayData.get(CommonUtil.DaySlotFragmentConst.NumberKey).toString()));
//        TextView tvNumbe = convertView.findViewById(R.id.tvNumber);
//        tvNumbe.setText(oneDayData.get(CommonUtil.HomeFragmentConst.NumberKey).toString());
        return convertView;
    }
}
