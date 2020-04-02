package com.felix.dtbs.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Process;
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

public class HomeFragmentAdapter extends ArrayAdapter<Map<String, Object>> {
    public HomeFragmentAdapter(@NonNull Context context, int resource, List<Map<String, Object>> data) {
        super(context, resource, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Map<String, Object> oneDayData = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_item, parent, false);
        }

        TextView tvWeek = convertView.findViewById(R.id.tvWeek);
        tvWeek.setText(oneDayData.get(CommonUtil.HomeFragmentConst.DayOfWeek).toString());
        TextView tvCalender = convertView.findViewById(R.id.tvCalener);
        tvCalender.setText(oneDayData.get(CommonUtil.HomeFragmentConst.DateKey).toString());
        ProgressBar pb = convertView.findViewById(R.id.progressBar);
        int color = (int)oneDayData.get(CommonUtil.HomeFragmentConst.ColorKey);
        pb.setProgressTintList(ColorStateList.valueOf(color));
        pb.setMax(CommonUtil.TotalPerDay);
        pb.setProgress(Integer.parseInt(oneDayData.get(CommonUtil.HomeFragmentConst.NumberKey).toString()));
        TextView tvNumbe = convertView.findViewById(R.id.tvNumber);
        tvNumbe.setText(oneDayData.get(CommonUtil.HomeFragmentConst.NumberKey).toString());
        return convertView;
    }
}
