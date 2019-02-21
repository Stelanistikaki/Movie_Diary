package com.example.admin.movie_diary;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private Button addMovieButton, viewAllButton, searchButton;
    private Spinner searchSpinner;
    private EditText searchText;
    //private ImageView logoImage;
    private String[] searchValues = {"Title" , "Year", "Director", "Genre", "Rating", "Recommend"};
    DatabaseHelper mDatabaseHelper;
    String selectedCol, selectedText;
    boolean recommendSelected;
    ViewPager viewPager;
    TabLayout indicator;
    ArrayList<String> recentMovies = new ArrayList<>();

    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        addMovieButton = findViewById(R.id.addMovie);
        viewAllButton = findViewById(R.id.viewAllMovies);
        searchButton = findViewById(R.id.searchButton);
        searchText = findViewById(R.id.searchText);
        searchSpinner = findViewById(R.id.searchSpinner);
        //logoImage = findViewById(R.id.logoImage);
        mDatabaseHelper = new DatabaseHelper(this);
        viewPager= findViewById(R.id.viewPagerRecentlyAdded);
        indicator= findViewById(R.id.indicator);

        //logoImage.setImageResource(R.drawable.moviediary1);
        searchText.setFocusableInTouchMode(true);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, searchValues); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        searchSpinner.setAdapter(spinnerArrayAdapter);

        Cursor recent = mDatabaseHelper.getRecentData();
        while(recent.moveToNext()){
            recentMovies.add("'" + recent.getString(1) + "'");
        }

        viewPager.setAdapter(new ViewPagerAdapter(this, recentMovies));
        indicator.setupWithViewPager(viewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 10000, 6000);

        //if the chosen column is recommended it is only go show as result the movies that are recommended = true
        //there is no point in showing movies that are recommended = false right?
        searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCol = searchSpinner.getSelectedItem().toString();
                if (selectedCol.equals("Recommend")) {
                    searchText.setEnabled(false);
                    recommendSelected = true;
                }
                else{
                    searchText.setEnabled(true);
                    recommendSelected = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //that is not really possible
            }
        });


        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
                startActivity(intent);
            }
        });

        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = mDatabaseHelper.getData();
                if(data.getCount() == 0)
                    toastMessage("There are no movies yet!");
                else{
                    Intent intent  = new Intent(MainActivity.this, ListDataActivity.class);
                    startActivity(intent);
                }
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recommendSelected)
                    selectedText = "true";
                else
                    selectedText = searchText.getText().toString();
                Intent intent = new Intent(MainActivity.this, SearchDataActivity.class);
                intent.putExtra("value", selectedText);
                intent.putExtra("col", selectedCol);
                startActivity(intent);
                searchText.setText(" ");

            }
        });
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < recentMovies.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
