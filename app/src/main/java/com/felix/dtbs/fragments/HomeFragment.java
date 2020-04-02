package com.felix.dtbs.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.felix.dtbs.CommonUtil;
import com.felix.dtbs.MainActivity;
import com.felix.dtbs.R;
import com.felix.dtbs.models.Slot;
import com.felix.dtbs.service.BookSlotService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Map<String, Object>> overAllData;

    public HomeFragment() {
        Date cursor = new Date();
        overAllData = new ArrayList<>();
        Map<String, Object> oneDayData;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        for (int i = 0; i < 5; i++) {
            oneDayData = new HashMap<>();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(cursor);
            calendar.add(calendar.DATE, 1);
            cursor = calendar.getTime();
            if(CommonUtil.isWeekend(calendar)){
                cursor = CommonUtil.getNextDay(true, cursor);
                calendar.setTime(cursor);
            }

            List<Slot> slots =  BookSlotService.getInstance().getSlots(CommonUtil.SimpleDateFormat.format(cursor));
            oneDayData.put(CommonUtil.HomeFragmentConst.DateKey, CommonUtil.SimpleDateFormat.format(cursor));
            int amount = slots.size();
            oneDayData.put(CommonUtil.HomeFragmentConst.NumberKey, amount);

            oneDayData.put(CommonUtil.HomeFragmentConst.DayOfWeek, simpleDateFormat.format(cursor));
            oneDayData.put(CommonUtil.HomeFragmentConst.ColorKey, CommonUtil.getColor(amount, CommonUtil.TotalPerDay));
            overAllData.add(oneDayData);

        }

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    private Activity mainActivity;
    private ListView lvContent;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = getActivity();
        lvContent = getView().findViewById(R.id.lvContent);
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(mainActivity, R.layout.fragment_home_item, overAllData);
        lvContent.setAdapter(adapter);
        getView().findViewById(R.id.tvGoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mActivity = (MainActivity)mainActivity;
                mActivity.onOptionClicked(1, null);
            }
        });
    }
}
