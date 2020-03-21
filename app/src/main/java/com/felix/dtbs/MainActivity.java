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
import com.felix.dtbs.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.views.DuoOptionView;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {
    private  MenuItemAdapter menuAdapter;
    DuoMenuView mDuoMenuView;
    private DuoDrawerLayout draw;
    private ArrayList<String> menuItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        draw = findViewById(R.id.drawer);
        menuItems.add("Home");
        menuItems.add("Book a slot");
        menuItems.add("View slots by driver");
        menuItems.add("View slots by day");
        menuItems.add("View everyday's slots");
        menuAdapter = new MenuItemAdapter(menuItems);
        mDuoMenuView = findViewById(R.id.menu);
        mDuoMenuView.setOnMenuClickListener(this);

        //getSupportFragmentManager().beginTransaction().replace(R.id.placeHolder, new HomeFragment()).commit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDuoMenuView.setAdapter(menuAdapter);

            }
        }, 100);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //View content = draw.findViewWithTag("content");
        //content.setVisibility(View.GONE);
    }

    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        //setTitle(mTitles.get(position));

        // Set the right options selected
        DuoOptionView selectedView = (DuoOptionView)menuAdapter.setViewSelected(position, true);
        String menuText = menuItems.get(position).toLowerCase();
        // Navigate to the right fragment
        switch (menuText) {
            case "home":

                break;
            case "book a slot":
                goToFragment(new BookSlotFragment(), false);
                break;
            default:
                goToFragment(new HomeFragment(), false);
                break;
        }
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction ;
        List<Fragment> fragments =  fragmentManager.getFragments();
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