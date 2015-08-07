package in.storewalk.storewalksellerapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.storewalk.storewalksellerapp.R;
import in.storewalk.storewalksellerapp.ui.fragment.EnterPhNoFragment;
import in.storewalk.storewalksellerapp.ui.fragment.StockViewFragment;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by Sathish Mun on 04-07-2015.
 */

public class MainScreenTabActivity extends AppCompatActivity implements MaterialTabListener {

    private MaterialTabHost tabHost;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting the view
        setContentView(R.layout.activity_main_screen_tab);

        // initialize the tabs and the view pager
        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);


        //set up adapter
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });

        // function to execute on changing the tabs
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {

        // changing the tab when the tab is clicked
        viewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }


    //  FRAGMENTA is just used to check whether fragments is working.... can be removed when the profile tab is being set
    public static class FragmentA extends Fragment  //JAVA CLASS FOR FRAGMENT 1
    {
        private TextView textView;

        public static FragmentA getInstance(int position) {
            FragmentA myFragment = new FragmentA();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment1, container, false);
            textView = (TextView) layout.findViewById(R.id.textView);
            Bundle bundle = getArguments();
            if (bundle != null) {
                textView.setText("The Page selected is " + bundle.getInt("position"));
            }
            return layout;
        }
    }


    class MyPagerAdapter extends FragmentPagerAdapter  //for tabs
    {
        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs_mainscreen);
        }

        // getting the tab titles from the array initialized
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        // setting up fragment for each tab
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Fragment myFragment = null;
            if (position == 0) {
                myFragment = new EnterPhNoFragment();
            }
            if (position == 1) {

                myFragment = new StockViewFragment();
            }
            if (position == 2) {

                myFragment = new FragmentA();
            }


            return myFragment;
        }

        //getting the count of the number of tabs
        @Override
        public int getCount() {
            return 3;
        }
    }


}
