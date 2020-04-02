package com.felix.dtbs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class CommonUtil {
    public static List<String> getTimeSlots() {
        List<String> slots = new ArrayList<>();
        for (int i = 9; i < 17; i++) {
            slots.add(String.valueOf(i) + ":00");
        }
        return slots;
    }

    public static List<String> getAvaliableDates() {
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

    public static final String Home = "Home";
    public static final String BOOK_SLOT = "Book a slot";
    public static final String VIEW_BY_DRIVER = "View slots by driver";
    public static final String VIEW_BY_DAY = "View slots by day";

    public static final int MAX_SLOT = 10;

    public static final int TotalPerDay = MAX_SLOT * 8;

    public class HomeFragmentConst {
        public static final String DateKey = "dateKey";
        public static final String DayOfWeek = "DayOfWeek";
        public static final String NumberKey = "Number";
        public static final String ColorKey = "ColorKey";
    }

    public class DaySlotFragmentConst {
        public static final String TimeKey = "TimeKey";
        public static final String NumberKey = "Number";
        public static final String ColorKey = "ColorKey";
    }

    /***
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.isEmpty()) {
            return true;
        }
        return false;
    }

    public static String[] getMenu() {
        return new String[]{Home, BOOK_SLOT, VIEW_BY_DRIVER, VIEW_BY_DAY};
    }

    public static SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static Date getNextDay(boolean isWorkDay) {
        Date today = new Date();
        return getNextDay(isWorkDay, today);
    }

    public static Date getNextDay(boolean isWorkDay, Date today) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        while (CommonUtil.isWeekend(calendar) == isWorkDay) {
            calendar.add(calendar.DATE, 1);
            tomorrow = calendar.getTime();
        }

        return tomorrow;
    }

    public static String getNextDayString(boolean isWorkDay) {
        Date tomorrow = getNextDay(isWorkDay);
        return CommonUtil.SimpleDateFormat.format(tomorrow);
    }

    public static HashMap<String, Object> getSlotItem(String driverLicense, Date slotDate, String slotTime) {
        HashMap<String, Object> slot = new HashMap<>();
        slot.put("tvDriverLicence", driverLicense);
        slot.put("tvSlotDate", SimpleDateFormat.format(slotDate));
        slot.put("tvSlotTime", slotTime);

        return slot;
    }

    public static boolean isWeekend(Calendar calendar) {
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }

        return false;
    }

    public static int getColor(int part, int total) {
        float pert = part * 100 / total;
        if (pert >= 75) {
            return Color.RED;
        }
        if (pert >= 50) {
            return Color.YELLOW;
        }

        return Color.GREEN;
    }

    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }
}
