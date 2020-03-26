package com.felix.dtbs;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;

import nl.psdcompany.duonavigationdrawer.views.DuoOptionView;

public class MenuItemAdapter extends BaseAdapter {
    private ArrayList<String> mOptions = new ArrayList<>();
    private ArrayList<View> mOptionViews = new ArrayList<>();

    public MenuItemAdapter(ArrayList<String> options) {
        mOptions = options;
    }

    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return mOptions.get(position);
    }

    public View setViewSelected(int position, boolean selected) {

        // Looping through the options in the menu
        // Selecting the chosen option
        View selectedView = null;
        for (int i = 0; i < mOptionViews.size(); i++) {
            if (i == position) {
                selectedView = mOptionViews.get(i);
                selectedView.setSelected(selected);
            } else {
                mOptionViews.get(i).setSelected(!selected);
            }
        }

        return  selectedView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String option = mOptions.get(position);

        // Using the DuoOptionView to easily recreate the demo
        final DuoOptionView optionView;
        if (convertView == null) {
            optionView = new DuoOptionView(parent.getContext());
        } else {
            optionView = (DuoOptionView) convertView;
        }

        // Using the DuoOptionView's default selectors
        optionView.setClickable(true);

        //optionView.setEnabled(true);
        optionView.bind(option, null, null);

        // Adding the views to an array list to handle view selection
        mOptionViews.add(optionView);

        return optionView;
    }
}
