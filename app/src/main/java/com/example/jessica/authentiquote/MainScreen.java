package com.example.jessica.authentiquote;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class MainScreen extends Activity {

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    Resources res;
    String prevColor;
    String prevQuote;
    String[] quotes;
    String[] authors;
    String[] colors;
    TextView mainQuote;
    TextView mainAuthor;

    //set txtQuote to a random quote in our "quotes" array.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mainQuote = (TextView) findViewById(R.id.txtQuote);
        mainAuthor = (TextView) findViewById(R.id.txtAuthor);

        Typeface quote = Typeface.createFromAsset(getAssets(), "fonts/coolvetica rg.ttf");
        mainQuote.setTypeface(quote);

        Typeface author = Typeface.createFromAsset(getAssets(), "fonts/basictitlefont.ttf");
        mainAuthor.setTypeface(author);

        res = getResources();
        quotes = res.getStringArray(R.array.quotes);
        authors = res.getStringArray(R.array.authors);
        colors = res.getStringArray(R.array.colors);

        updateQuote(mainQuote, mainAuthor);
        updateColor(mainQuote);

        //shake gesture
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            //change quote to another random
            public void onShake() {

                Resources res = getResources();
                final String[] toastMsgs = res.getStringArray(R.array.toastMsgs);
                Random toastGen = new Random();
                int randomIndex = toastGen.nextInt(toastMsgs.length);

                Toast toast = Toast.makeText(getApplicationContext(),
                        toastMsgs[randomIndex], Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

                updateQuote(mainQuote, mainAuthor);
                updateColor(mainQuote);


            }
        });

    }


    public void updateQuote(TextView mainQuote, TextView mainAuthor) {

        Random generator = new Random();
        int randomIndex = generator.nextInt(quotes.length);

        //check and make sure not duplicate quote back to back
        if (quotes[randomIndex] != prevQuote) {
            mainQuote.setText(quotes[randomIndex]);
            mainAuthor.setText(authors[randomIndex]);
        } else {
            if (randomIndex < (quotes.length - 1)) {

                randomIndex++;

            } else
                randomIndex = 0;

            mainQuote.setText(quotes[randomIndex]);
            mainAuthor.setText(authors[randomIndex]);
        }

        prevQuote = quotes[randomIndex];


    }


    public void updateColor(TextView mainQuote) {

        Random colorGen = new Random();
        int colorIndex = colorGen.nextInt(colors.length);

        //setting quote to random color
        //check and make sure not duplicate colors back to back
        if (colors[colorIndex] != prevColor) {
            mainQuote.setTextColor(Color.parseColor(colors[colorIndex]));
        } else {
            if (colorIndex < (colors.length - 1)) {

                colorIndex++;

            } else
                colorIndex = 0;

            mainQuote.setTextColor(Color.parseColor(colors[colorIndex]));
        }

        prevColor = colors[colorIndex];

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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

    //gesture detection
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    public void socialMedia(View view) {
        Toast toast = null;
        switch (view.getId()) {
          case R.id.facebook:
               String userEntry = mainQuote.getText().toString() + "\n" + mainAuthor.getText().toString();
               Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.setType("text/plain");
                textShareIntent.setPackage("com.facebook.katana");
                textShareIntent.putExtra(Intent.EXTRA_TEXT, userEntry);

                try{
                    startActivity(textShareIntent);
                } catch (ActivityNotFoundException ex) {


                }

              break;

        case R.id.twitter:
                String userEntry2 = mainQuote.getText().toString() + "\n" + mainAuthor.getText().toString();
                Intent textShareIntent2 = new Intent(Intent.ACTION_SEND);
                textShareIntent2.setType("text/plain");
                textShareIntent2.setPackage("com.twitter.android");
                textShareIntent2.putExtra(Intent.EXTRA_TEXT, userEntry2);

                try {
                    startActivity(textShareIntent2);
                } catch (ActivityNotFoundException ex) {
                }
                break;

            case R.id.tumblr:
                String userEntry3 = mainQuote.getText().toString() + "\n" + mainAuthor.getText().toString();
                Intent textShareIntent3 = new Intent(Intent.ACTION_SEND);
                textShareIntent3.setType("text/plain");
                textShareIntent3.setPackage("com.tumblr");
                textShareIntent3.putExtra(Intent.EXTRA_TEXT, userEntry3);

                try {
                    startActivity(textShareIntent3);
                } catch (ActivityNotFoundException ex) {
                }
                break;


            case R.id.plus:
                String userEntry4 = mainQuote.getText().toString() + "\n" + mainAuthor.getText().toString();
                Intent textShareIntent4 = new Intent(Intent.ACTION_SEND);
                textShareIntent4.putExtra(Intent.EXTRA_TEXT, userEntry4);
                textShareIntent4.setType("text/plain");
                startActivity(textShareIntent4);
                break;
        }
    }
}



