package com.nasserapps.bsc.ui.HomePage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nasserapps.bsc.R;
import com.nasserapps.bsc.ui.AboutDialogFragment;


public class HomeActivity extends ActionBarActivity {

    FragmentManager fm;
    boolean x=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().setBackgroundDrawable(null);

        fm = getFragmentManager();

        if (fm.findFragmentById(R.id.container) == null) {
            RecyclerViewFragment list = new RecyclerViewFragment();
            fm.beginTransaction().add(R.id.container, list).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
            AboutDialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), "About");
            return true;

        }

        if (id == R.id.grid) {

            Fragment newFragment;
            if(x){
                newFragment = new BalancedScorecardFragment();
                x=false;
            }
            else{
                newFragment = new RecyclerViewFragment();
                x=true;
            }

            fm.beginTransaction().replace(R.id.container,newFragment).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
