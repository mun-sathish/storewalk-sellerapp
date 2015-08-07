package in.storewalk.storewalksellerapp.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.storewalk.storewalksellerapp.R;
import in.storewalk.storewalksellerapp.adapter.RecylerAdapter;
import in.storewalk.storewalksellerapp.dto.DrawerItemDTO;
import in.storewalk.storewalksellerapp.ui.activities.StockViewActivity;


/**
 * Created by Sathish Mun on 04-07-2015.
 */
public class NavigationDrawerFragment extends Fragment implements RecylerAdapter.ClickListener {


    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private RecyclerView recyclerView;
    private View containerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecylerAdapter adaptor;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static List<DrawerItemDTO> getData() {
        List<DrawerItemDTO> data = new ArrayList<>();
        int[] icons = {R.mipmap.shopping, R.mipmap.price_tag, R.mipmap.barcode, R.mipmap.billing};
        String[] titles = {"Stock View", "Discount", "DashBoard", "Reviews"};
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            DrawerItemDTO current = new DrawerItemDTO();
            current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }

    public static void saveToPreferences(Context context, String preferanceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferanceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String prefereceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(prefereceName, defaultValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerlist);
        adaptor = new RecylerAdapter(getActivity(), getData());
        adaptor.setClickListener(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerlayout, final Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerlayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    @Override
    public void itemClicked(View view, final int position) {
/*

         // to  increse the time of the ripple view
        Thread t = new Thread() {

            public void run() {
                try {
                    Thread.sleep(220);
                    if (position == 0) {
                        startActivity(new Intent(getActivity(), Subactivity.class));
                        mDrawerLayout.closeDrawer(containerView);
                    }

                    if (position == 1) {
                        startActivity(new Intent(getActivity(), Tabs.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        if (position == 1) {
            startActivity(new Intent(getActivity(), Subactivity.class));

            mDrawerLayout.closeDrawer(containerView);

        }

        if (position == 2) {
            startActivity(new Intent(getActivity(), Tabs.class));
            mDrawerLayout.closeDrawer(containerView);
        }

        if (position == 2) {
            startActivity(new Intent(getActivity(), AppTutorialScreenActivity.class));
            mDrawerLayout.closeDrawer(containerView);
        }

  */
        if (position == 1) {
            startActivity(new Intent(getActivity(), StockViewActivity.class));
            mDrawerLayout.closeDrawer(containerView);
        }

    }
}
