package com.example.usuario.finalapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.usuario.finalapplication.adapters.DrawerAdapter;


public class Main extends Activity implements ListFragment.OnFragmentInteractionListener {

    private ListView list;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    public final static String ADD_ITEMS="Add data";
    public final static String EDIT_ITEMS="Edit data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setAdapter();
        setClicks();
        setToggle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawerToggle.syncState();
    }

    private void setToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                R.drawable.ic_drawer,
                R.string.action_settings,
                R.string.action_settings);
        drawer.setDrawerListener(mDrawerToggle);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setSubtitle(ADD_ITEMS);
    }

    private void setClicks() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(Main.this, "Menu Item: " + DrawerAdapter.menu[position], Toast.LENGTH_SHORT).show();
                drawer.closeDrawers();
                openFragment(position);
            }
        });
    }

    private void openFragment(int position) {
        switch (position) {
            case 0:
                addFragment("Hola","Mundo");break;
        }
    }

    private void addFragment(String param1, String param2) {
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame, ListFragment.newInstance(param1, param2)).commit();
    }

    private void initUI() {
        list = (ListView) findViewById(R.id.left_drawer);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void setAdapter() {
        DrawerAdapter adapter = new DrawerAdapter(this);
        list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.menu_add:getActionBar().setSubtitle(ADD_ITEMS);break;
            case R.id.menu_edit:getActionBar().setSubtitle(EDIT_ITEMS);break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(Main.this, message, Toast.LENGTH_SHORT).show();
    }
}
