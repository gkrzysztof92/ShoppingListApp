package com.sysmobil.shoppinglistapp.app;


import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.adapters.TabsAdapter;
import com.sysmobil.shoppinglistapp.database.DatabaseHelper;
import com.sysmobil.shoppinglistapp.fragments.MyShoppingListFragment;
import com.sysmobil.shoppinglistapp.fragments.PaidShoppingListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Main activity class.
 */
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
        
        init();
        
        prepareDataResource();

        adapter = new TabsAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Initialize ui elements and layout
     */
    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        toolbar.setTitle("Lista Zakupów");
    }

    /**
     * Creating instance of fragment class
     */
    private void prepareDataResource() {
        addData(new MyShoppingListFragment(), "Moje Listy Zakupów");
        addData(new PaidShoppingListFragment(), "Zapłacone Listy Zakupów");
    }

    /**
     * Adds fragments to fragment lists and titles of fragments to titles lists.
     * @param fragment
     * @param title
     */
    private void addData(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

}
