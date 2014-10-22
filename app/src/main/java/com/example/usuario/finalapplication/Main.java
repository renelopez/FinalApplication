package com.example.usuario.finalapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.usuario.finalapplication.adapters.DrawerAdapter;
import com.example.usuario.finalapplication.adapters.ForecastAdapter;
import com.example.usuario.finalapplication.model.ModelGet;
import com.example.usuario.finalapplication.net.NetServices;
import com.example.usuario.finalapplication.net.OnBackgroundTaskCallback;
import com.example.usuario.finalapplication.net.ProgressBarAnimation;
import com.example.usuario.finalapplication.utils.OnFragmentInteractionListener;
import com.google.gson.Gson;

import static com.example.usuario.finalapplication.utils.Utils.Fragments;


public class Main extends Activity implements OnFragmentInteractionListener {

    public final static String ADD_ITEMS="Add data";
    public final static String EDIT_ITEMS="Edit data";
    private ListView list;
    private Fragments activeFragment;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private ForecastAdapter forecastAdapter;

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
                loadData();
                openFragment(position);
            }
        });
    }

    private void openFragment(int position) {
        FragmentManager manager = getFragmentManager();
        switch (position) {
            case 0:
                manager.beginTransaction().replace(R.id.content_frame, ListFragment.newInstance("", ""), "ListFragment").commit();
                activeFragment = Fragments.LIST_FRAGMENT;
                break;
            case 1:
                manager.beginTransaction().replace(R.id.content_frame, GridFragment.newInstance("", ""), "GridFragment").commit();
                activeFragment = Fragments.GRID_FRAGMENT;
                break;
            case 2:
                manager.beginTransaction().replace(R.id.content_frame, BothFragment.newInstance("", ""), "BothFragment").commit();
                activeFragment = Fragments.BOTH_FRAGMENT;
                break;
        }
    }

    private void initUI() {
        list = (ListView) findViewById(R.id.left_drawer);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
    }

    private void setAdapter() {
        DrawerAdapter adapter = new DrawerAdapter(this);
        list.setAdapter(adapter);
    }

    private void loadData() {
        new NetServices(new OnBackgroundTaskCallback() {
            @Override
            public void onTaskCompleted(String response) {
                //mListener.makeToast("We have data!!!!");
                parseJSON(response);
            }

            @Override
            public void onTaskError(String error) {
                //mListener.makeToast("Error while communicating.."+error);
            }
        }, new ProgressBarAnimation(findViewById(R.id.progress_bar))).execute(NetServices.WS_CALL_GET);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        ModelGet model = gson.fromJson(response, ModelGet.class);
        setAdapter(model);
    }

    private void setAdapter(ModelGet model) {
        Fragment fragment;
        forecastAdapter = new ForecastAdapter(this, model.getQuery().getResults().getChannel().getItem().getForecast());
        switch (this.activeFragment) {
            case LIST_FRAGMENT:
                fragment = getFragmentManager().findFragmentByTag("ListFragment");
                ((ListView) (fragment.getView().findViewById(R.id.listForecast))).setAdapter(this.forecastAdapter);
                break;
            case GRID_FRAGMENT:
                fragment = getFragmentManager().findFragmentByTag("GridFragment");
                ((GridView) (fragment.getView().findViewById(R.id.gridForecast))).setAdapter(this.forecastAdapter);
                break;
            case BOTH_FRAGMENT:
                fragment = getFragmentManager().findFragmentByTag("BothFragment");
                ((ListView) (fragment.getView().findViewById(R.id.bothListForecast))).setAdapter(this.forecastAdapter);
                ((GridView) (fragment.getView().findViewById(R.id.bothGridForecast))).setAdapter(this.forecastAdapter);
                break;
        }

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
