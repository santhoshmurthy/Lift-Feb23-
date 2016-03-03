package com.LAW.lift.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LAW.lift.R;
import com.LAW.lift.adapter.CardArrayAdapter;
import com.LAW.lift.adapter.mainadapter;
import com.LAW.lift.app.LiftApplication;
import com.LAW.lift.app.MyVolley;
import com.LAW.lift.fragments.AboutFragment;
import com.LAW.lift.fragments.BookingFragment;
import com.LAW.lift.fragments.ContactusFragment;
import com.LAW.lift.fragments.DisclaimerFragment;
import com.LAW.lift.fragments.HomeFragment;
import com.LAW.lift.fragments.HowtouseFragment;
import com.LAW.lift.model.Card;
import com.LAW.lift.model.MainCard;
import com.LAW.lift.model.MyTextviewWhite;
import com.LAW.lift.navdrawer.NavDrawerItem;
import com.LAW.lift.navdrawer.NavDrawerListAdapter;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends FragmentActivity {
    private Button central,Tamil,supreme,Madras,forum;
    private TextView mon;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ListView listView;
    public static String[] get_legislation;
    public static String[] Id;
    public static String[] Book_Name;
    public static String[] Url;
    public static String[] Published_Month;
    public static String[] month;
    private mainadapter adaps;
    String bookid,yearss;
    ProgressDialog pDialog;
    public static String months;
public static String years="2016";
    public static String Language="English";
    String urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final/lift_of_the_month.php?id=1";
    private RelativeLayout mDrawerRelativeLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;



    /* Calendar mcurrentTime = Calendar.getInstance();
     final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
     final int minute = mcurrentTime.get(Calendar.MINUTE);
     final int seconds=mcurrentTime.get(Calendar.SECOND);
     final int milliseconds=mcurrentTime.get(Calendar.MILLISECOND);
     final int date=mcurrentTime.get(Calendar.DATE);*/
    // used to store app title
    private CharSequence mTitle;
    Button lang;
    Locale myLocale;
    String eng="English";
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ImageButton btnspeak;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LiftApplication.getInstance().trackScreenView("Main activity");

        android.app.ActionBar ab = getActionBar();

        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {


            bookid = extras.getString("book_id");
            months=extras.getString("month");
            years=extras.getString("year");

            Log.d("Mainactivity",bookid+months+years);

        }



        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.title_view, null);
        ((MyTextviewWhite) v.findViewById(R.id.title)).setText(this.getTitle());
        this.getActionBar().setCustomView(v);





        mTitle = mDrawerTitle = getTitle();





        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
       navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);






        navDrawerItems = new ArrayList<>();

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
       /* getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);*/

        /*mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){*/
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.menu_icon, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                hideKeyboard();
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);


        }

        lang=(Button) findViewById(R.id.button2);
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Language = lang.getText().toString();
                Log.d("Language", "" + Language);
                setLocale("ta");
                if (Language.equals(eng)) {

                    setLocale("");
                }





            }
        });











    }






        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
    public void setLocale(String lang) {
        // lang.setText("English");
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(MainActivity.this,MainActivity.class);

        startActivity(refresh);
        MainActivity.this.finish();
    }
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // toggle nav drawer on selecting action bar app icon/title
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            // Handle action bar actions click
		/*switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}*/
            return super.onOptionsItemSelected(item);
        }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
        @Override
        public boolean onPrepareOptionsMenu (Menu menu){
            // if nav drawer is opened, hide the action items
            // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerRelativeLayout);
            //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
            return super.onPrepareOptionsMenu(menu);
        }

/*

    private void displayView(int position) {

        android.support.v4.app. Fragment fragment = null;
        switch (position) {
            case 0:
                break;
            case 1:
                Intent DomainIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(DomainIntent);
                MainActivity.this.finish();
                break;
            case 2:
                Intent l=new Intent(MainActivity.this,Library.class);
                startActivity(l);
                finish();
                break;
            case 3:
                Bundle bundle=new Bundle();
                fragment = new AboutFragment();
                fragment.setArguments(bundle);
                break;

            case 4:
                Bundle bundle2 = new Bundle();
                fragment = new DisclaimerFragment();
                fragment.setArguments(bundle2);
                break;
            case 5:

                Bundle bundle1=new Bundle();
                fragment = new HowtouseFragment();
                fragment.setArguments(bundle1);
                break;
            case 6:
                Bundle bundles=new Bundle();
                fragment = new ContactusFragment();
                fragment.setArguments(bundles);

                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().addToBackStack("Frag1")
                    .replace(R.id.frame_container, fragment).commit();


            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            //mDrawerLayout.closeDrawer(mDrawerList);
            mDrawerLayout.closeDrawer(mDrawerRelativeLayout);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
*/




    /**
         * Diplaying fragment view for selected nav drawer list item
         */
    private void displayView(int position) {
        // update the main content by replacing fragments
        android.support.v4.app.Fragment fragment = null;

        switch (position) {
            case 0:
                Bundle bundle3=new Bundle();
                fragment = new HomeFragment();
                bundle3.putString("book_id",bookid);
                bundle3.putString("month",months);
                bundle3.putString("year",years);
                Log.d("ma",bookid+months+years);
                fragment.setArguments(bundle3);
                break;

            case 1:
                Intent l=new Intent(MainActivity.this,Library.class);
                startActivity(l);
        /*        MainActivity.this.finish();*/
                break;
            case 2:
                Intent in=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(in);
                break;
            case 3 :
                Bundle bundle=new Bundle();
                fragment = new AboutFragment();
                fragment.setArguments(bundle);
                break;
            case 4:
                Bundle bundles=new Bundle();
                fragment = new ContactusFragment();
                fragment.setArguments(bundles);

                break;
            case 5:
                Bundle bundle2 = new Bundle();
                fragment = new DisclaimerFragment();
                fragment.setArguments(bundle2);

                break;
            case 6:
                Bundle bundle1=new Bundle();
                fragment = new HowtouseFragment();
                fragment.setArguments(bundle1);

                break;



                default:
                    break;
            }

           {
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().addToBackStack("Frag1")
                    .replace(R.id.frame_container, fragment).commit();


            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            //mDrawerLayout.closeDrawer(mDrawerList);
            mDrawerLayout.closeDrawer(mDrawerRelativeLayout);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            hideKeyboard();
            displayView(position);
        }
    }











    @Override
    public void onBackPressed() {
        MainActivity.this.finish();
       /* if (getFragmentManager().findFragmentByTag("Frag1") != null) {
            getFragmentManager().popBackStackImmediate("Frag1", 0);

        } else {*//**/
            /*super.onBackPressed();
            Intent in = new Intent(MainActivity.this, MainActivity.class);
            startActivity(in);*/

        }


    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }


}




