package com.felix.dtbs.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.felix.dtbs.CommonUtil;
import com.felix.dtbs.R;
import com.felix.dtbs.models.Slot;
import com.felix.dtbs.service.BookSlotService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverSlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverSlotFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DriverSlotFragment() {
    }

    private String currentDriver;

    public DriverSlotFragment(String driverLicence) {
        currentDriver = driverLicence;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriverSlotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriverSlotFragment newInstance(String param1, String param2) {
        DriverSlotFragment fragment = new DriverSlotFragment();
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

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_slot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getView().findViewById(R.id.lvdriverslot);
        BookSlotService instance = BookSlotService.getInstance();
        AutoCompleteTextView autoCompleteTextView = getView().findViewById(R.id.autoCompleteTextView);
        List<String> driverLicences = instance.getDriverLicences();
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtil.hideKeyboard(getActivity());
                String driverLicence = driverLicences.get(position);
                SetSlots(driverLicence, autoCompleteTextView);
            }
        });
        ArrayAdapter atuolistAdapter = new ArrayAdapter(getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                driverLicences);

        autoCompleteTextView.setAdapter(atuolistAdapter);

        if (!CommonUtil.isNullOrEmpty(currentDriver)) {
            SetSlots(currentDriver, autoCompleteTextView);
        }
    }

    public void SetSlots(String driverLicence, AutoCompleteTextView autoCompleteTextView) {
        autoCompleteTextView.setText(driverLicence);
        List<Slot> slots = BookSlotService.getInstance().getTimeslotBooking(driverLicence);
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for (Slot slot : slots) {
            listItem.add(CommonUtil.getSlotItem(slot.getDriverLicence(), slot.getSlotDate(), slot.getSlotTime()));
        }
        SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(), listItem, R.layout.fragment_driver_slot_item,
                new String[]{"tvSlotDate", "tvSlotTime"},
                new int[]{R.id.slotDate, R.id.slotTime});
        listView.setAdapter(listItemAdapter);
    }


}
