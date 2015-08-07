package in.storewalk.storewalksellerapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.storewalk.storewalksellerapp.R;

/**
 * Created by Sathish Mun on 04-07-2015.
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread t = new Thread() {

            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent b = new Intent(getApplicationContext(), AppTutorialScreenActivity.class);
                    startActivity(b);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }
}
