package com.felix.dtbs.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.felix.dtbs.CommonUtil;
import com.felix.dtbs.MainActivity;
import com.felix.dtbs.R;
import com.felix.dtbs.components.DatePickerFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookSlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookSlotFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookSlotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookSlotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookSlotFragment newInstance(String param1, String param2) {
        BookSlotFragment fragment = new BookSlotFragment();
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
        return inflater.inflate(R.layout.fragment_book_slot, container, false);
    }
    private MainActivity activity;
    private TextView etDate;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner slotsSelector = getView().findViewById(R.id.spinnerTimeSelector);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, CommonUtil.getTimeSlots());
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        slotsSelector.setAdapter(adapter);
        activity = (MainActivity)getActivity();

        etDate = getView().findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setPositiveClickListner(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker datePicker = ((DatePickerDialog)dialog).getDatePicker();
                        int day = datePicker.getDayOfMonth();
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        etDate.setText(String.valueOf(day)+"/" + String.valueOf(month) + "/" + String.valueOf(year));
                    }
                });
                newFragment.show(activity.getSupportFragmentManager(), "datePicker");

            }
        });
    }
}
