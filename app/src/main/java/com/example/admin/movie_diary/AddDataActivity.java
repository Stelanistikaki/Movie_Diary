package com.example.admin.movie_diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class AddDataActivity extends AppCompatActivity {

    private EditText titleText, yearText, directorText, commentsText;
    private Spinner genreSpinner;
    private Switch recommendSwitch;
    private RatingBar ratingStars;
    private Button saveButton;
    private DatabaseHelper mDatabaseHelper;
    private String[] movieGenres = {"Select a genre" , "Action", "Drama", "Comedy", "Thriller", "Horror", "Fantasy", "Sci-fi", "Anime"};
    private static final String TAG = AddDataActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie_activity);

        mDatabaseHelper = new DatabaseHelper(this);
        titleText = findViewById(R.id.titleText);
        yearText = findViewById(R.id.yearText);
        directorText = findViewById(R.id.directorText);
        commentsText = findViewById(R.id.commentsText);
        recommendSwitch = findViewById(R.id.recommendSwitch);
        ratingStars = findViewById(R.id.movieRatingBar);
        genreSpinner = findViewById(R.id.genreSpinner);
        saveButton = findViewById(R.id.saveButton);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, movieGenres); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        genreSpinner.setAdapter(spinnerArrayAdapter);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                String year = yearText.getText().toString();
                String director = directorText.getText().toString();
                String genre = genreSpinner.getSelectedItem().toString();
                String comments = commentsText.getText().toString();
                float ratingStarsNumber = ratingStars.getRating();
                boolean recommendBoolean = recommendSwitch.isChecked();
                String recommend = String.valueOf(recommendBoolean);

                boolean isInserted = mDatabaseHelper.insertData(title, year, director, genre, ratingStarsNumber, recommend, comments);
                if(isInserted) {
                    toastMessage("You watched another one? Cool!");
                    finish();
                }
                else
                    toastMessage("I didn't quite catch that");
            }
        });

    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
