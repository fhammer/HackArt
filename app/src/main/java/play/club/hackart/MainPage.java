package play.club.hackart;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import play.club.hackart.reciver.ScreenStatuManager;
import play.club.skecher.SkecherFragment;
import play.club.skecher.style.StylesFactory;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton mFab;
    private ScreenStatuManager mStatuManager;
    private SkecherFragment mSkecherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        mStatuManager = new ScreenStatuManager(this);
//        ScreenStstusListener listener = new ScreenStstusListener(this);
//        mStatuManager.begin(listener);

        mSkecherFragment = new SkecherFragment();
        replaceFrament(mSkecherFragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            return true;
        } else if (id == R.id.action_bgcolors) {
            return true;
        } else if (id == R.id.action_colors) {
            return true;
        } else if (id == R.id.action_radius) {
            return true;
        } else if (id == R.id.action_save) {
            return true;
        } else if (id == R.id.action_width) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_lines) {
            // Handle the camera action
            mSkecherFragment.getSurface().setStyle(StylesFactory.getStyle(StylesFactory.SIMPLE));
        } else if (id == R.id.nav_fur) {
            mSkecherFragment.getSurface().setStyle(StylesFactory.getStyle(StylesFactory.FUR));
        } else if (id == R.id.nav_silk) {
            mSkecherFragment.getSurface().setStyle(StylesFactory.getStyle(StylesFactory.RIBBON));
        } else if (id == R.id.nav_ribbon) {
            mSkecherFragment.getSurface().setStyle(StylesFactory.getStyle(StylesFactory.RIBBON));
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFrament(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, fragment);
        transaction.commit();
        mFab.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
//        mStatuManager.unregisterListener();
        super.onDestroy();
    }
}
