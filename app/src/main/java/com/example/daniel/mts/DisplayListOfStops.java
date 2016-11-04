package com.example.daniel.mts;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayListOfStops extends AppCompatActivity implements OnFragmentInteractionListener{
    ListView  listview;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView line;
    private TextView lineName;
    private TextView directionName;
    private ImageButton reverse;
    private View view;
    private boolean direction = false;
    private LinearLayout top;
    private Line lineInfo;

    public String getId(){
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("SelectedProperty");
        return id;
    }

    public boolean getDirection() {
        return direction;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stoplistlineinfobar_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        String id = bundle.getString("SelectedProperty");
        lineInfo = new Line(id);

        String col = "#" + lineInfo.color;
        String name = lineInfo.shortName;
        String longName = lineInfo.longName;
        String dir = lineInfo.directionName;

        line = (TextView) findViewById(R.id.txtitem);
        lineName = (TextView) findViewById(R.id.nameItem);
        directionName = (TextView) findViewById(R.id.direction);
        reverse = (ImageButton) findViewById(R.id.reverse);
        top = (LinearLayout) findViewById(R.id.stop_tool);
        GradientDrawable tvBackground = (GradientDrawable) line.getBackground();
        tvBackground.setColor(Color.parseColor(col));

        line.setText(name);
        line.setTextColor(Color.WHITE);

        view = line.getRootView();

        lineName.setText(longName);
        lineName.setTextColor(Color.BLACK);

        directionName.setText("To " + dir);
        directionName.setTextColor(Color.DKGRAY);

        //find drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        //Tie drawerlayout events to the action bar toggle for open and close
        mDrawer.addDrawerListener(drawerToggle);

        //Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        //Setup drawer view
        setUpDrawerContent(nvDrawer);
        Fragment fragment = null;
        Class fragmentClass = ListofStops.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentTransaction def = getSupportFragmentManager().beginTransaction();
        def.replace(R.id.flContent, fragment);
        def.commit();

        Button homeButton = (Button)findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.home_button: {
                        Fragment fragment = null;
                        Class fragmentClass = HomeFragment.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentTransaction def = getSupportFragmentManager().beginTransaction();
                        def.replace(R.id.flContent, fragment);
                        def.commit();
                    }
                }
            }
        });
        ImageButton reverse = (ImageButton)view.findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Class fragmentClass = null;
                Log.d("direction", direction + "");
                if(direction){
                    fragmentClass = StopReverseFragment.class;
                    Log.d("In Fragment:", " StopReverse");

                    Line oppLineInfo = new Line(lineInfo.oppositeDirectionId);
                    String dir = oppLineInfo.directionName;
                    directionName.setText("To " + dir);
                }
                else{
                    String dir = lineInfo.directionName;
                    directionName.setText("To " + dir);
                    fragmentClass = ListofStops.class;
                    Log.d("In Fragment:", " ListofStops");
                }
                direction = !direction;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                FragmentTransaction def = getSupportFragmentManager().beginTransaction();
                        def.replace(R.id.flContent, fragment);
                        def.commit();
            }


        });

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.home_frag:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.map_frag:
                fragmentClass = MapFragment.class;
                break;
            case R.id.fav_frag:
                fragmentClass = FavFragment.class;
                break;
            case R.id.lines_frag:
                fragmentClass = LinesFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        line.setVisibility(View.GONE);
        lineName.setVisibility(View.GONE);
        directionName.setVisibility(View.GONE);
        reverse.setVisibility(View.GONE);
        top.setVisibility(View.GONE);
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Sync toggle state after onrestore has happened
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFragmentMessage(String MSG, Object data) {

    }


}

