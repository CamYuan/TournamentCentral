package com.example.cy1023.tournamentcentral;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.content.res.Configuration;
import android.app.FragmentManager;



public class MainActivity extends Activity {
    private ShareActionProvider shareActionProvider;
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;
    //use for database Query so they don't have to log in every time/
    public String local_userID = "cam";
    public String local_password = "aaa" ;
    public boolean signedIn = true;


    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            selectItem(position);
        }
    }//;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.menu);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState != null){
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }else{
            selectItem(0);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        if (fragment instanceof TopFragment) {
                            currentPosition = 0;
                        }
                        if (fragment instanceof TournamentsFragment) {
                            currentPosition = 1;
                        }
                        if (fragment instanceof TeamsFragments) {
                            currentPosition = 2;
                        }
                        if (fragment instanceof LoginActivity || fragment instanceof MyProfileFragment) {
                            currentPosition = 3;
                        }
                        setActionBarTitle(currentPosition);
                        drawerList.setItemChecked(currentPosition, true);
                    }
                }
        );

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.host_tourney).setVisible(signedIn);
        menu.findItem(R.id.create_team).setVisible(signedIn);
        menu.findItem(R.id.sign_out).setVisible(signedIn);
        return super.onPrepareOptionsMenu(menu);
    }

    private void selectItem(int position) {
        currentPosition = position;
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new TournamentsFragment();
                break;
            case 2:
                fragment = new TeamsFragments();
                break;
            case 3:
                if (signedIn) {
                    fragment = new MyProfileFragment();
                }
                else {fragment = new LoginActivity();}
                break;
            default:
                fragment = new TopFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("position", currentPosition);
    }
    public void setActionBarTitleFromFragment(String title){
        this.getActionBar().setTitle(title);
    }

    private void setActionBarTitle(int position){
        String title;
        if (position ==0){
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //MenuItem menuItem=menu.findItem(R.id.host_tourney);
        //shareActionProvider=(ShareActionProvider) menuItem.getActionProvider();
        //setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }
    /*private void setIntent(String text){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        shareActionProvider.setShareIntent(intent);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.host_tourney:
                HostFragment Hostfragment = new HostFragment();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, Hostfragment, null)
                        .addToBackStack(null)
                        .commit();
                drawerLayout.closeDrawer(drawerList);
                /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Must be logged in to host a Tournament.")
                        .setNegativeButton("OK", null)
                        .create()
                        .show();*/
                return true;
            case R.id.create_team:
                CreateTeamFragment createFrag = new CreateTeamFragment();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, createFrag, null)
                        .addToBackStack(null)
                        .commit();
                drawerLayout.closeDrawer(drawerList);
            case R.id.action_settings:
                //TODO: implement settings?
                return true;
            case R.id.sign_out:
                local_userID = null;
                local_password = null;
                signedIn = false;
                TopFragment backHome = new TopFragment();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, backHome, null)
                        .addToBackStack(null)
                        .commit();
                drawerLayout.closeDrawer(drawerList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
