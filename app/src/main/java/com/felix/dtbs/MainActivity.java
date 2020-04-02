package com.felix.dtbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.felix.dtbs.fragments.BookSlotFragment;
import com.felix.dtbs.fragments.DaySlotFragment;
import com.felix.dtbs.fragments.DriverSlotFragment;
import com.felix.dtbs.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.views.DuoOptionView;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {
    private MenuItemAdapter menuAdapter;
    DuoMenuView mDuoMenuView;
    private ArrayList<String> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        menuItems = new ArrayList<>(Arrays.asList(CommonUtil.getMenu()));
        menuAdapter = new MenuItemAdapter(menuItems);
        mDuoMenuView = findViewById(R.id.menu);
        mDuoMenuView.setOnMenuClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDuoMenuView.setAdapter(menuAdapter);
            }
        }, 1000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //View content = draw.findViewWithTag("content");
        //content.setVisibility(View.GONE);
    }

    @Override
    public void onFooterClicked() {
        finish();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "Assignment from Felix", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        additionalOptionClicked(position, null, objectClicked, null);
    }

    public void additionalOptionClicked(int position, String menuText, Object objectClicked, Object state) {
        menuAdapter.setViewSelected(position, true);
        if (CommonUtil.isNullOrEmpty(menuText)) {
            menuText = menuItems.get(position);
        }
        // Navigate to the right fragment
        switch (menuText) {
            case CommonUtil.Home:
                goToFragment(new HomeFragment(), false);
                break;
            case CommonUtil.BOOK_SLOT:
                goToFragment(new BookSlotFragment(), false);
                break;
            case CommonUtil.VIEW_BY_DRIVER:
                goToFragment(new DriverSlotFragment(state== null ? null : state.toString()), false);
                break;
            case CommonUtil.VIEW_BY_DAY:
                goToFragment(new DaySlotFragment(), false);
                break;
            default:
                //goToFragment(new HomeFragment(), false);
                break;
        }
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction;
        List<Fragment> fragments = fragmentManager.getFragments();
        Log.i("dtbs", "fragments count is " + fragments.size());

        for (Fragment ff : fragments) {
            transaction = fragmentManager.beginTransaction();
            transaction.detach(ff);
            transaction.remove(ff);
            transaction.commit();
        }

        transaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.container, fragment).commit();
    }
}
