package com.sysmobil.shoppinglistapp.app;


import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.adapters.TabsAdapter;
import com.sysmobil.shoppinglistapp.database.DatabaseHelper;
import com.sysmobil.shoppinglistapp.fragments.MyShoppingListFragment;
import com.sysmobil.shoppinglistapp.fragments.PaidShoppingListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private Context context;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private ViewPager viewPager;
    private TabsAdapter adapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this.getApplicationContext();
        databaseHelper = new DatabaseHelper(this.context);
        
        initialsise();
        
        prepareDataResource();

        adapter = new TabsAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initialsise() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        toolbar.setTitle("Shopping List App");


    }

    private void prepareDataResource() {

        addData(new MyShoppingListFragment(), "My Shopping Lists");
        addData(new PaidShoppingListFragment(), "Paid Shopping Lists");
    }

    private void addData(Fragment fragment, String title) {

        fragmentList.add(fragment);
        titleList.add(title);
    }

    void showSnackBar() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Added shopping list", Snackbar.LENGTH_SHORT);
        View snackBarlayout = snackbar.getView();
        snackBarlayout.setBackgroundColor(Color.BLUE);
        TextView txvMessage = (TextView) snackBarlayout.findViewById(R.id.snackbar_text);
        txvMessage.setTextColor(Color.YELLOW);
        snackbar.show();
    }


}
