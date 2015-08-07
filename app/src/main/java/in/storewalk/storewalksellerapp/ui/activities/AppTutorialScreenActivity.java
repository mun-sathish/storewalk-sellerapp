package in.storewalk.storewalksellerapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import in.storewalk.storewalksellerapp.R;
import in.storewalk.storewalksellerapp.adapter.AppTutorialAdapter;
import in.storewalk.storewalksellerapp.ui.widget.CirclePageIndicator;
import in.storewalk.storewalksellerapp.ui.widget.PageIndicator;


/**
 * Created by Sathish Mun on 04-07-2015.
 */


public class AppTutorialScreenActivity extends Activity implements View.OnClickListener {


    private PageIndicator pageIndicator;
    private ViewPager viewPager;
    private Button btnSkipTutorial;
    private int[] tutorialScreensList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setView
        setContentView(R.layout.app_tutorial);

        // initialize variables
        viewPager = (ViewPager) findViewById(R.id.viewPager_app_tutorial);
        pageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        btnSkipTutorial = (Button) findViewById(R.id.btn_skip_tutorial);

        initViewPager();

        btnSkipTutorial.setOnClickListener(this);

    }

    private void initViewPager() {    //set View Pager
        tutorialScreensList = new int[]{R.drawable.video, R.drawable.video, R.drawable.video};
        viewPager.setAdapter(new AppTutorialAdapter(this, tutorialScreensList));
        pageIndicator.setViewPager(viewPager);
        pageIndicator.onPageScrolled(viewPager.getCurrentItem(), 0, 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_skip_tutorial:
                Intent b = new Intent(getApplicationContext(), MainScreenTabActivity.class);
                startActivity(b);
                finish();
                break;

        }

    }


}