package com.felix.dtbs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommonUtil {
    public static List<String> getTimeSlots(){
        List<String> slots = new ArrayList<>();
        for (int i = 9; i < 17 ; i++) {
            slots.add(String.valueOf(i) + ":00");
        }
        return slots;
    }

    public static List<String> getAvaliableDates(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("dd/MM/yyyy");// a为am/pm的标记
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        List<String> dates = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date = calendar.getTime();
            dates.add(sdf.format(date));
        }
        return dates;
    }
}
