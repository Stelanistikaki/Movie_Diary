package com.example.admin.movie_diary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button addMovieButton, viewAllButton, searchButton;
    private Spinner searchSpinner;
    private EditText searchText;
    private String[] searchValues = {"Title" , "Year", "Director", "Genre", "Rating", "Recommend"};
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMovieButton = findViewById(R.id.addMovie);
        viewAllButton = findViewById(R.id.viewAllMovies);
        searchButton = findViewById(R.id.searchButton);
        searchText = findViewById(R.id.searchText);
        searchSpinner = findViewById(R.id.searchSpinner);
        mDatabaseHelper = new DatabaseHelper(this);

        searchText.setFocusableInTouchMode(true);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, searchValues); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        searchSpinner.setAdapter(spinnerArrayAdapter);


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
                String selectedText = searchText.getText().toString();
                String selectedCol = searchSpinner.getSelectedItem().toString();

                Intent intent = new Intent(MainActivity.this, SearchDataActivity.class);
                intent.putExtra("value", selectedText);
                intent.putExtra("col", selectedCol);
                startActivity(intent);

            }
        });
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
